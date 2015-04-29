package org.xpertss.json.types;

import org.junit.Before;
import org.junit.Test;

import java.util.TimeZone;

import static org.junit.Assert.*;
import static xpertss.json.JSON.*;

public class TimezoneTypeTest {

   private TimeZone timeZone;
   private TimezoneType objectUnderTest;

   private String timeZoneString = "America/New_York";



   @Before
   public void setUp() throws Exception
   {
      timeZone = TimeZone.getTimeZone(timeZoneString);
      objectUnderTest = new TimezoneType();
   }


   @Test
   public void testMarshal() throws Exception
   {
      assertEquals(string(timeZoneString), objectUnderTest.marshall(timeZone));
   }

   @Test(expected = NullPointerException.class)
   public void testMarshalNull() throws Exception
   {
      objectUnderTest.marshall(null);
   }


   @Test
   public void testUnmarshall()
   {
      assertEquals(timeZone, objectUnderTest.unmarshall(string(timeZoneString)));
   }

   @Test(expected = NullPointerException.class)
   public void testUnmarshallNull()
   {
      objectUnderTest.unmarshall(NULL);
   }



}