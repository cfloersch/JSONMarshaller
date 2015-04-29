package org.xpertss.json.types;

import org.junit.Before;
import org.junit.Test;
import xpertss.json.MarshallingException;

import java.util.Currency;

import static org.junit.Assert.assertEquals;
import static xpertss.json.JSON.string;

/**
 * Created by cfloersch on 2/4/2015.
 */
public class CurrencyTypeTest {

   private static final String USD = "USD";
   private static final String EURO = "EUR";
   private static final String CAD = "CAD";

   private Currency dollar;
   private Currency euro;
   private Currency candian;
   private CurrencyType objectUnderTest;

   @Before
   public void setUp() throws Exception
   {
      dollar = Currency.getInstance("USD");
      euro = Currency.getInstance("EUR");
      candian = Currency.getInstance("CAD");
      objectUnderTest = new CurrencyType();
   }

   @Test
   public void testMarshalDollar()
   {
      assertEquals(string(USD), objectUnderTest.marshall(dollar));
   }

   @Test
   public void testUnmarshallDollar()
   {
      assertEquals(dollar, objectUnderTest.unmarshall(string(USD)));
   }

   @Test
   public void testMarshalEuro()
   {
      assertEquals(string(EURO), objectUnderTest.marshall(euro));
   }

   @Test
   public void testUnmarshallEuro()
   {
      assertEquals(euro, objectUnderTest.unmarshall(string(EURO)));
   }


   @Test
   public void testMarshalCanadianDollar()
   {
      assertEquals(string(CAD), objectUnderTest.marshall(candian));
   }

   @Test
   public void testUnmarshallCanadianDollar()
   {
      assertEquals(candian, objectUnderTest.unmarshall(string(CAD)));
   }


   @Test(expected = MarshallingException.class)
   public void testUnmarshallInvalidCode()
   {
      assertEquals(dollar, objectUnderTest.unmarshall(string("EURO")));
   }

}
