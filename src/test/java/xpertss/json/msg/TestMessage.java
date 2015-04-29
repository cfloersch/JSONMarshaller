package xpertss.json.msg;

import org.junit.Test;
import xpertss.json.JSONMarshaller;
import xpertss.json.JSONObject;

import static org.junit.Assert.*;

/**
 * Created by cfloersch on 6/19/2014.
 */
public class TestMessage {


   // this will test that self referencing entities work properly..
   // In this case Message is an entity but it also contains a
   // reference to itself in the form of the attachment objects.

   @Test
   public void testMarshallMessage()
   {
      JSONMarshaller<Message> marshaller = JSONMarshaller.create(Message.class);
      LoginMessage msg = new LoginMessage("cfloersch", UserType.Buyer);
      msg.addAttachment(new BidderRegMessage(5133456L,12));
      JSONObject encoded = marshaller.marshall(msg);

      System.out.println(encoded);

      Message decoded = marshaller.unmarshall(encoded);
      assertEquals(msg.ident(), decoded.ident());
      assertTrue(decoded instanceof LoginMessage);
      assertEquals(1, decoded.getAttachmentCount());
   }
}
