package org.xpertss.json.types;

import org.junit.Before;
import org.junit.Test;
import xpertss.json.JSON;

import java.net.URI;

import static org.junit.Assert.*;

public class URITypeTest {

   private static final String URI_STR = "http://www.google.com/";

   private URIType objectUnderTest;
   private URI uri;

   @Before
   public void setUp()
   {
      objectUnderTest = new URIType();
      uri = URI.create(URI_STR);
   }

   @Test
   public void testMarshal()
   {
      assertEquals(JSON.string(URI_STR), objectUnderTest.marshall(uri));
   }

   @Test
   public void testUnmarshal()
   {
      assertEquals(uri, objectUnderTest.unmarshall(JSON.string(URI_STR)));
   }

}