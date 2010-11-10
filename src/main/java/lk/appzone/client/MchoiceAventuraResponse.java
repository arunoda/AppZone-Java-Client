/**
	Copyright [2010] [Arunoda Susiripala]
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
		http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */

package lk.appzone.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

/**
 * $LastChangedDate: $
 * $LastChangedBy: $
 * $LastChangedRevision: $
 */

public class MchoiceAventuraResponse {

    private final String xmlResponse;
    private String statusCode;
    private String statusMessage;
    private String version;
    private String correlator;

    private static Logger logger = LoggerFactory.getLogger(MchoiceAventuraResponse.class);

    public MchoiceAventuraResponse(String xmlResponse) throws MchoiceAventuraMessagingException {

        this.xmlResponse = xmlResponse;
        this.parseXML(xmlResponse);
    }

    private void parseXML(String xmlRecords) throws MchoiceAventuraMessagingException {

        try {
            DocumentBuilderFactory dbf =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xmlRecords));

            Document doc = db.parse(is);
            NodeList nodes = doc.getElementsByTagName("mchoice_sdp_sms_response");

            if (nodes.getLength() == 1) {
                Element response = (Element) nodes.item(0);
                this.version = this.getField(response, "version");
                this.correlator = this.getField(response, "correlator");
                this.statusCode = this.getField(response, "status_code");
                this.statusMessage = this.getField(response, "status_message");

            } else {
                throw new MchoiceAventuraMessagingException("More than one, mchoice_sdp_sms_response :" + xmlRecords);
            }


        }
        catch (Exception e) {

            logger.error("Invalid Response: " + xmlRecords, e);
            throw new MchoiceAventuraMessagingException("Invalid Response: " + xmlRecords, e);
        }
    }

    public String getField(Element parent, String requesFiled) {

        NodeList name = parent.getElementsByTagName(requesFiled);
        Element e = (Element) name.item(0);
        if (e == null) {
            throw new IllegalArgumentException("There is no such field");
        }

        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "";
    }

    public String getStatusCode() {
        return this.statusCode;
    }

    public String getStatusMessage() {
        return this.statusMessage;
    }

    public String getCorrelator() {
        return this.correlator;
    }

    public String getVersion() {
        return this.version;
    }

    public boolean isSuccess() {
        return ("SBL-SMS-MT-2000".equals(this.statusCode));
    }

    public String toString() {
        return this.version + ":" + this.statusCode + ":"
                + this.statusMessage + ":" + this.correlator;
    }

}
