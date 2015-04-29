package org.xpertss.json.types;

import org.junit.Before;
import org.junit.Test;
import xpertss.json.JSON;
import xpertss.json.JSONString;
import xpertss.json.MarshallingException;

import java.util.Random;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * Created by cfloersch on 2/5/2015.
 */
public class UUIDTypeTest {

   private UUID uuid;
   private UUIDType objectUnderTest;

   @Before
   public void setUp() {
      Random rand = new Random();
      objectUnderTest = new UUIDType();
      uuid = new UUID(rand.nextLong(), rand.nextLong());
   }

   @Test
   public void testEncodeDecodeCycle() throws Exception {
      JSONString encoded = objectUnderTest.marshall(uuid);
      UUID decoded = objectUnderTest.unmarshall(encoded);
      assertEquals(uuid, decoded);
   }

   @Test(expected = MarshallingException.class)
   public void testInvalidUUIDInput() throws Exception {
      JSONString encoded = JSON.string("c180b7ffd3fa2bf9f44cf50ad758619a");
      objectUnderTest.unmarshall(encoded);
   }

}
