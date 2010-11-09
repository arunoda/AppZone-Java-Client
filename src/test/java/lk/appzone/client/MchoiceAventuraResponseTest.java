package lk.appzone.client;

import junit.framework.TestCase;

/**
 * Created by IntelliJ IDEA.
 * User: arunoda
 * Date: Aug 10, 2010
 * Time: 9:22:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class MchoiceAventuraResponseTest extends TestCase {

    String xmlString;
    MchoiceAventuraResponse resp;

    public void setUp() throws Exception {
        this.xmlString = "<mchoice_sdp_sms_response>" +
                "    <version>1.0</version>" +
                "    <correlator>10081008480007</correlator>" +
                "    <status_code>400</status_code>" +
                "    <status_message>Message</status_message>" +
                " </mchoice_sdp_sms_response>";
        resp = new MchoiceAventuraResponse(xmlString);
    }

    public void testGettters() {

        assertEquals("1.0", resp.getVersion());
        assertEquals("10081008480007", resp.getCorrelator());
        assertEquals("400", resp.getStatusCode());
        assertEquals("Message", resp.getStatusMessage());
    }

    public void testForTwoResponsesInSame() {
        try {
            MchoiceAventuraResponse resp = new MchoiceAventuraResponse( "<d>" +
                    this.xmlString + " \n" +  this.xmlString + "</d>");

            fail();
        }
        catch (Exception ex) {

        }
    }

    public void testForNoResponsesString() {
        try {
            MchoiceAventuraResponse resp = new MchoiceAventuraResponse("");
            fail();
        }
        catch (Exception ex) {

        }
    }

}
