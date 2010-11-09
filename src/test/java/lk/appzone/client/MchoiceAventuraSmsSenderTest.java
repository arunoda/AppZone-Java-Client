package lk.appzone.client;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

import junit.framework.TestCase;
import sun.misc.BASE64Encoder;


/**
 * Created by IntelliJ IDEA.
 * User: arunoda
 * Date: Aug 9, 2010
 * Time: 6:37:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class MchoiceAventuraSmsSenderTest extends TestCase{

	/**
	 * If need to test start the simulator and configure with the urls
	 * @throws Exception
	 */
    public void _testSend() throws Exception{
        MchoiceAventuraSmsSender sender = new MchoiceAventuraSmsSender("http://204.236.220.126:65182/services/","PD_ET_Y0220","b9c82d5f305251a34de73bd78e120eaa");
        MchoiceAventuraResponse resp = sender.sendMessage("Test Message","3AAZ110qxx%2FYz9xk9WMbxOByZZiARnh96LURyxB");
        System.out.println(resp);
        assertTrue(resp.isSuccess());
    }
    
    public void testBase64() throws UnsupportedEncodingException {
    	
    	for(int lc = 0; lc< 1000; lc++) {
    		
    		String word = "ddd" + lc;
        	BASE64Encoder sun = new BASE64Encoder();
        	String sunE = sun.encode(word.getBytes());
        	
        	Base64 apache = new Base64();
        	String apacheE = apache.encodeToString(word.getBytes());
        	apacheE = apacheE.substring(0, apacheE.length() -2);
        	
        	System.out.println(sunE + "-");
        	System.out.println(apacheE.substring(0, apacheE.length() -2) + "-");
        	
        	assertTrue(apacheE.equals(sunE));
    	}
    	
    	Base64 apache = new Base64();
    	String apacheE = apache.encodeToString("PD_ET_Y0220:b9c82d5f305251a34de73bd78e120eaa".getBytes("UTF8"));
    	apacheE = apacheE.substring(0, apacheE.length() -2);
  
    	System.out.println(apacheE + "||||||||||");
    	assertEquals("UERfRVRfWTAyMjA6YjljODJkNWYzMDUyNTFhMzRkZTczYmQ3OGUxMjBlYWE=", apacheE);
    }
}
