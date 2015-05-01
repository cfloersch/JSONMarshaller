package org.xpertss.json.types;

import org.junit.Before;
import org.junit.Test;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static xpertss.json.JSON.string;

public class TimeTypeTest {

   private static final String TIME_STR = "18:25:43.511Z";


   private Time date;
   private TimeType objectUnderTest;


   @Before
   public void setUp() throws Exception
   {
      DateFormat format = new SimpleDateFormat("HH:mm:ss.SSSX");
      format.setTimeZone(TimeZone.getTimeZone("UTC"));

      date = new Time(format.parse(TIME_STR).getTime());
      objectUnderTest = new TimeType();
   }


   @Test
   public void testMarshal()
   {
      assertEquals(string(TIME_STR), objectUnderTest.marshall(date));
   }

   @Test
   public void testUnmarshall()
   {
      assertEquals(date, objectUnderTest.unmarshall(string(TIME_STR)));
   }

}