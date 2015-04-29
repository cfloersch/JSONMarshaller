package org.xpertss.json.types;

import org.junit.Before;
import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static xpertss.json.JSON.string;

/**
 * Created by cfloersch on 6/17/2014.
 */
public class LocaleTypeTest {

   private Locale locale;
   private LocaleType objectUnderTest;

   private String localeStr = "fr-CA";

   @Before
   public void setUp()
   {
      locale = Locale.CANADA_FRENCH;
      objectUnderTest = new LocaleType();
   }


   @Test
   public void testMarshall()
   {
      assertEquals(string(localeStr), objectUnderTest.marshall(locale));
   }

   @Test
   public void testUnmarshall()
   {
      assertEquals(locale, objectUnderTest.unmarshall(string(localeStr)));
   }

   @Test
   public void testUnmarshallGibberish()
   {
      assertEquals(new Locale("yes", "Sir"), objectUnderTest.unmarshall(string("yes_Sir")));
   }

}
