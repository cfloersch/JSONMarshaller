package org.xpertss.json.types;

import org.junit.Before;
import org.junit.Test;
import xpertss.json.JSON;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import static org.junit.Assert.*;
import static xpertss.json.JSON.*;

public class TimestampTypeTest {

   private static final String DATE_STR = "2012-04-23T18:25:43.511Z";


   private Timestamp date;
   private TimestampType objectUnderTest;


   @Before
   public void setUp() throws Exception
   {
      DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
      format.setTimeZone(TimeZone.getTimeZone("UTC"));

      date = new Timestamp(format.parse(DATE_STR).getTime());
      objectUnderTest = new TimestampType();
   }


   @Test
   public void testMarshal()
   {
      assertEquals(string(DATE_STR), objectUnderTest.marshall(date));
   }

   @Test
   public void testUnmarshall()
   {
      assertEquals(date, objectUnderTest.unmarshall(string(DATE_STR)));
   }

}