package lk.appzone.client;

import junit.framework.TestCase;

/**
 * Created by IntelliJ IDEA.
 * User: arunoda
 * Date: Aug 10, 2010
 * Time: 9:22:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class MchoiceAventuraMessagingExceptionTest extends TestCase{

    public void testConstructors(){
        new MchoiceAventuraMessagingException("message",new Exception());
        new MchoiceAventuraMessagingException("message");
        new MchoiceAventuraMessagingException();
    }

}