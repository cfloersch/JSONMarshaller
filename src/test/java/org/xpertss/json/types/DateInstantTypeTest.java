package org.xpertss.json.types;

import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static xpertss.json.JSON.string;

public class DateInstantTypeTest {

   private static final String DATE_STR = "2012-04-23T18:25:43.511Z";


   private Date date;
   private DateInstantType objectUnderTest;


   @Before
   public void setUp() throws Exception
   {
      DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
      format.setTimeZone(TimeZone.getTimeZone("UTC"));

      date = format.parse(DATE_STR);
      objectUnderTest = new DateInstantType();
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