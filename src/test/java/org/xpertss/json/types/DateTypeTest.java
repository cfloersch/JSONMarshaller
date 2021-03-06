package org.xpertss.json.types;

import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static xpertss.json.JSON.string;

public class DateTypeTest {

   private static final String DATE_STR = "2012-04-23";


   private Date date;
   private DateType objectUnderTest;


   @Before
   public void setUp() throws Exception
   {
      DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
      format.setTimeZone(TimeZone.getTimeZone("UTC"));

      date = new Date(format.parse(DATE_STR).getTime());
      objectUnderTest = new DateType();
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