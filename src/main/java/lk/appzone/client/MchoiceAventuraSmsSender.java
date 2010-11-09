/*
 *   (C) Copyright 2010-2011 hSenid Software International (Pvt) Limited.
 *   All Rights Reserved.
 *
 *   These materials are unpublished, proprietary, confidential source code of
 *   hSenid Software International (Pvt) Limited and constitute a TRADE SECRET
 *   of hSenid Software International (Pvt) Limited.
 *
 *   hSenid Software International (Pvt) Limited retains all title to and intellectual
 *   property rights in these materials.
 *
 */

package lk.appzone.client;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * $LastChangedDate: $
 * $LastChangedBy: $
 * $LastChangedRevision: $
 */

public class MchoiceAventuraSmsSender {

    private static Logger logger = LoggerFactory.getLogger(MchoiceAventuraSmsSender.class);

    private String httpHost;
    private final String authId;
    private final String password;


    public MchoiceAventuraSmsSender(String httpHost, String authId, String password) {

        this.httpHost = httpHost;
        this.authId = authId;
        this.password = password;
    }

    public MchoiceAventuraResponse sendMessage(String content, String address) throws MchoiceAventuraMessagingException {

        try {
            String urlParameters =
                    "version=" + URLEncoder.encode("1.0", "UTF-8") +
                            "&message=" + URLEncoder.encode(content, "UTF-8") +
                            "&address=" + URLEncoder.encode("tel:" + address, "UTF-8");

            if (logger.isDebugEnabled()) {
                logger.debug("requesting - httpHost:" + this.httpHost + "auth" + this.authId + ":" + this.password +
                        "\nurlParams: " + urlParameters);
            }

            String response = excutePost(this.httpHost, this.authId, this.password, urlParameters);
            logger.info("response :" + response);

            return new MchoiceAventuraResponse(response);

        } catch (UnsupportedEncodingException e) {
            throw new MchoiceAventuraMessagingException("Encoding error in urlParams", e);
        }
    }


    public String excutePost(String targetURL, String username, String password, String urlParameters) throws MchoiceAventuraMessagingException {
        URL url;
        HttpURLConnection connection = null;
        try {
            //Create connection
            url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();

            setSdpHeaderParams(connection);
            setBasicAuthentication(username, password, connection);

            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            sendRequest(urlParameters, connection);

            return getResponse(connection);


        } catch (Exception e) {
            logger.error("Error occcured while sending the request", e);
            throw new MchoiceAventuraMessagingException("Error occcured while sending the request", e);

        } finally {

            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private void sendRequest(String urlParameters, HttpURLConnection connection) throws IOException {

        if (logger.isDebugEnabled()) {
            logger.debug("sending the request: " + urlParameters);
        }
        //Send request
        DataOutputStream wr = new DataOutputStream(
                connection.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();
    }

    private String getResponse(HttpURLConnection connection) throws IOException {

        if (logger.isDebugEnabled()) {
            logger.debug("getting the response");
        }

        InputStream is = connection.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuffer response = new StringBuffer();
        while ((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }
        rd.close();
        return response.toString();
    }

    private void setSdpHeaderParams(HttpURLConnection connection) throws ProtocolException {
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Accept", "text/xml");
        connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
    }

    private void setBasicAuthentication(String username, String password, HttpURLConnection connection) {
        
        Base64 encoder = new Base64();
        String userpassword = username + ":" + password;
        String encodedAuthorization = encoder.encodeToString((userpassword.getBytes()));
        encodedAuthorization = encodedAuthorization.substring(0, encodedAuthorization.length() - 2);
        
        logger.debug("base64 encoded username:password is: {}", encodedAuthorization);
        connection.setRequestProperty("Authorization", "Basic " +
        encodedAuthorization);
         
    }
}
