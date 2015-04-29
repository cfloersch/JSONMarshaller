package org.xpertss.json.types;

import org.junit.Before;
import org.junit.Test;
import xpertss.json.MarshallingException;

import javax.activation.MimeType;

import static org.junit.Assert.*;
import static xpertss.json.JSON.*;

/**
 * Created by cfloersch on 6/17/2014.
 */
public class MimeTypeTest {


   private MimeType mimeType;
   private MimeTypeType objectUnderTest;

   /*
   text/html; charset=ISO-8859-4
   America/New_York
      */

   private String mimeString = "text/html; charset=ISO-8859-4";


   @Before
   public void setUp() throws Exception
   {
      mimeType = new MimeType(mimeString);
      objectUnderTest = new MimeTypeType();
   }


   @Test
   public void testMarshal() throws Exception
   {
      assertEquals(string(mimeString), objectUnderTest.marshall(mimeType));
   }

   @Test(expected = NullPointerException.class)
   public void testMarshalNull() throws Exception
   {
      objectUnderTest.marshall(null);
   }


   @Test
   public void testUnmarshall()
   {
      assertEquals(mimeType.toString(), objectUnderTest.unmarshall(string(mimeString)).toString());
   }

   @Test(expected = NullPointerException.class)
   public void testUnmarshallNull()
   {
      objectUnderTest.unmarshall(NULL);
   }

   @Test(expected = MarshallingException.class)
   public void testUnmarshallInvalid()
   {
      objectUnderTest.unmarshall(string("hello there"));
   }

}
