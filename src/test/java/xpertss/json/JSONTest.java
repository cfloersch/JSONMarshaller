/**
 * Created by IntelliJ IDEA.
 * User: cfloersch
 * Date: 4/8/11 1:55 PM
 * Copyright Manheim online
 */
package xpertss.json;

import static org.junit.Assert.*;
import static xpertss.json.JSON.*;

import org.junit.Test;
import org.xpertss.json.desc.BigDecimalDescriptor;
import org.xpertss.json.util.Numbers;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Random;

public class JSONTest {



   @Test
   public void testNull() throws Exception {
      JSONValue pn = JSON.parse("null");
      assertSame(JSON.NULL, pn);
      assertEquals("null", pn.toString());
      assertEquals("null", JSON.stringify(pn));
   }

   @Test(expected = SyntaxException.class)
   public void testInvalidNull() throws Exception {
      JSON.parse("nil");
   }




   @Test
   public void testBooleanTrue() throws Exception {
      JSONValue pn = JSON.parse("true");
      assertSame(JSON.TRUE, pn);
      assertEquals("true", pn.toString());
      assertEquals("true", JSON.stringify(pn));
   }

   @Test
   public void testBooleanFalse() throws Exception {
      JSONValue pn = JSON.parse("false");
      assertSame(JSON.FALSE, pn);
      assertEquals("false", pn.toString());
      assertEquals("false", JSON.stringify(pn));
   }



   @Test(expected = SyntaxException.class)
   public void testBooleanInvalidCapitalTrue() throws Exception {
      JSON.parse("TRUE");
   }

   @Test(expected = SyntaxException.class)
   public void testBooleanInvalidCamelTrue() throws Exception {
      JSON.parse("True");
   }

   @Test(expected = SyntaxException.class)
   public void testBooleanInvalidTrueTruncated() throws Exception {
      JSON.parse("tru");
   }

   @Test(expected = SyntaxException.class)
   public void testBooleanInvalidCapitalFalse() throws Exception {
      JSON.parse("FALSE");
   }

   @Test(expected = SyntaxException.class)
   public void testBooleanInvalidCamelFalse() throws Exception {
      JSON.parse("False");
   }

   @Test(expected = SyntaxException.class)
   public void testBooleanInvalidFalseTruncated() throws Exception {
      JSON.parse("fals");
   }



   @Test
   public void testShort() throws Exception {
      JSONValue pn = JSON.parse("2");
      JSONNumber on = number(Short.valueOf("2"));
      assertEquals(pn, on);
      assertEquals("2", pn.toString());
      assertEquals("2", JSON.stringify(pn));
   }

   @Test
   public void testInteger() throws Exception {
      JSONValue pn = JSON.parse("2");
      JSONNumber on = number(Integer.valueOf("2"));
      assertEquals(pn, on);
      assertEquals("2", pn.toString());
      assertEquals("2", JSON.stringify(pn));
   }

   @Test
   public void testLong() throws Exception {
      JSONValue pn = JSON.parse("2");
      JSONNumber on = number(Long.valueOf("2"));
      assertEquals(pn, on);
      assertEquals("2", pn.toString());
      assertEquals("2", JSON.stringify(pn));
   }

   @Test
   public void testFloat() throws Exception {
      JSONValue pn = JSON.parse("2.2");
      JSONNumber on = number(Float.parseFloat("2.2"));
      JSONNumber cn = number(2.2F);
      assertEquals(pn, on);
      assertEquals(pn, cn);
      assertEquals(on, cn);
      assertEquals("2.2", pn.toString());
      assertEquals("2.2", JSON.stringify(pn));
   }

   @Test
   public void testDouble() throws Exception {
      JSONValue pn = JSON.parse("2.2");
      JSONNumber on = number(Double.valueOf("2.2"));
      JSONNumber cn = number(2.2D);
      assertEquals(pn, on);
      assertEquals(pn, cn);
      assertEquals(on, cn);
      assertEquals("2.2", pn.toString());
      assertEquals("2.2", JSON.stringify(pn));
   }

   @Test
   public void testString() {
      JSONValue ps = JSON.parse("\"Hello\"");
      JSONString cs = string("Hello");
      assertEquals(ps, cs);
      assertEquals("\"Hello\"", ps.toString());
      assertEquals("\"Hello\"", JSON.stringify(ps));
   }

   @Test
   public void testObjectEmpty()
   {
      JSONObject obj = JSON.object();
      assertTrue(obj.isEmpty());
   }

   @Test
   public void testObjectVarArgs() {
      JSONValue  po = JSON.parse("{\"Hello\":2}");
      JSONObject co = JSON.object(string("Hello"), number(2));
      assertEquals(po, co);
      assertEquals("{\"Hello\":2}", JSON.stringify(po));
      assertEquals("{\n  \"Hello\":2\n}", po.toString());
   }

   @Test
   public void testArray() {
      JSONValue pa = JSON.parse("[2,2]");
      JSONArray ca = JSON.array(number(2), number(2));
      assertEquals(pa, ca);
      assertEquals("[2,2]", JSON.stringify(pa));
      assertEquals("[\n  2,\n  2\n]", pa.toString());
   }




   @Test(expected = IllegalArgumentException.class)
   public void testUnbalancedObjectCreate()
   {
      JSON.object(string("name"));
   }

   @Test(expected = IllegalArgumentException.class)
   public void testObjectCreateKeyNotString()
   {
      JSON.object(number(2), string("name"));
   }


   @Test(expected = SyntaxException.class)
   public void testUnbalancedArray() throws Exception {
      JSON.parse("[2,2");
   }

   @Test(expected = SyntaxException.class)
   public void testUnbalancedObject() throws Exception {
      JSON.parse("{\"Hello\":2");
   }

   @Test(expected = SyntaxException.class)
   public void testUnbalancedQuotes() throws Exception {
      JSON.parse("\"Hello");
   }

   @Test(expected = SyntaxException.class)
   public void testInvalidToken() throws Exception {
      JSON.parse("Hello");
   }

   @Test(expected = SyntaxException.class)
   public void testInvalidArrayExtraComma() throws Exception {
      JSON.parse("[2,2,]");
   }



   @Test
   public void testSimple() throws Exception
   {
      JSONObject container = (JSONObject) JSON.parse(load("/simple.json"));
      assertEquals(3, container.size());
      assertTrue(container.containsKey(string("age")));
      assertTrue(container.containsKey(string("name")));
      JSONArray array = (JSONArray) container.get(string("messages"));
      assertEquals(3, array.size());
   }

   @Test
   public void testGlossary() throws Exception {
      JSONObject container = (JSONObject) JSON.parse(load("/glossary.json"));
      assertEquals(1, container.size());
      JSONObject glossary = (JSONObject) container.get(string("glossary"));
      assertEquals(2, glossary.size());
      assertEquals(string("example glossary"), glossary.get(string("title")));
      JSONObject glossEntry = (JSONObject) glossary.get(string("GlossEntry"));
      assertEquals(7, glossEntry.size());
      assertEquals(string("ISO 8879:1986"), glossEntry.get(string("Abbrev")));
   }

   @Test
   public void testColors() throws Exception {
      JSONObject container = (JSONObject) JSON.parse(load("/colors.json"));
      assertEquals(1, container.size());
      JSONArray array = (JSONArray) container.get(string("colorsArray"));
      assertEquals(7, array.size());
      JSONObject color = (JSONObject) array.get(2);
      assertEquals(2, color.size());
      assertEquals(string("blue"), color.get(string("colorName")));
   }

   @Test
   public void testWidget() throws Exception {
      JSONObject container = (JSONObject) JSON.parse(load("/widget.json"));
      assertEquals(1, container.size());
      JSONObject widget = (JSONObject) container.get(string("widget"));
      assertEquals(4, widget.size());
      JSONObject window = (JSONObject) widget.get(string("window"));
      assertEquals(4, window.size());
      assertEquals(number(200), window.get(string("height")));
      assertEquals(number(500), window.get(string("width")));
   }



   @Test(expected = SyntaxException.class)
   public void testInvalidString() throws Exception
   {
      JSON.parse(load("/inv_string.json"));

   }

   @Test(expected = SyntaxException.class)
   public void testInvalidArray() throws Exception
   {
      JSON.parse(load("/inv_array.json"));

   }

   @Test(expected = SyntaxException.class)
   public void testInvalidObject() throws Exception
   {
      JSON.parse(load("/inv_object.json"));
   }


   @Test
   public void testStringifyOfString()
   {
      assertEquals("\"Hello\"", JSON.stringify(string("Hello")));
   }

   @Test
   public void testStringifyOfNumber()
   {
      assertEquals("2.5", JSON.stringify(number(2.5D)));
   }

   @Test
   public void testStringifyOfBoolean()
   {
      assertEquals("true", JSON.stringify(TRUE));
      assertEquals("false", JSON.stringify(FALSE));
   }

   @Test
   public void testStringifyOfNull()
   {
      assertEquals("null", JSON.stringify(NULL));
   }

   @Test
   public void testStringifyOfArray()
   {
      JSONArray array = array(number(1),number(2),number(3));
      assertEquals("[1,2,3]", JSON.stringify(array));
   }

   @Test
   public void testStringifyOfObject()
   {
      JSONObject object = object(string("high"), number(3),
                                 string("low"),number(1));
      assertEquals("{\"high\":3,\"low\":1}", JSON.stringify(object));
   }



   @Test(expected = ArithmeticException.class)
   public void testNumberLargeLong()
   {
      number(Long.MAX_VALUE);
   }

   @Test(expected = ArithmeticException.class)
   public void testNumberLargeLongByOne()
   {
      number(Numbers.MAX_LONG_AS_DOUBLE + 1L);
   }

   @Test(expected = ArithmeticException.class)
   public void testNumberSmallLong()
   {
      number(Long.MIN_VALUE);
   }

   @Test(expected = ArithmeticException.class)
   public void testNumberSmallLongByOne()
   {
      number(Numbers.MIN_LONG_AS_DOUBLE - 1L);
   }

   @Test(expected = ArithmeticException.class)
   public void testNumberLargeBigInteger()
   {
      number(BigInteger.probablePrime(128, new Random()));
   }

   @Test
   public void testNumberLargeBigDecimal()
   {
      number(BigDecimal.valueOf(Double.MAX_VALUE));
   }

   @Test(expected = ArithmeticException.class)
   public void testNumberLargeBigDecimalPlusTen()
   {
      BigDecimal value = BigDecimal.valueOf(Double.MAX_VALUE);
      number(value.add(BigDecimal.TEN));
   }



   @Test
   public void testStringWriteEmbeddedTab() throws Exception {
      JSONString str = JSON.string("Before\tAfter");

      StringWriter writer = new StringWriter();
      str.write(writer);
      assertEquals("\"Before\\tAfter\"", writer.toString());
   }

   @Test
   public void testStringWriteEmbeddedCarriageReturn() throws Exception {
      JSONString str = JSON.string("Before\rAfter");

      StringWriter writer = new StringWriter();
      str.write(writer);
      assertEquals("\"Before\\rAfter\"", writer.toString());
   }

   @Test
   public void testStringWriteEmbeddedLineFeed() throws Exception {
      JSONString str = JSON.string("Before\nAfter");

      StringWriter writer = new StringWriter();
      str.write(writer);
      assertEquals("\"Before\\nAfter\"", writer.toString());
   }

   @Test
   public void testStringWriteEmbeddedBackSpace() throws Exception {
      JSONString str = JSON.string("Before\bAfter");

      StringWriter writer = new StringWriter();
      str.write(writer);
      assertEquals("\"Before\\bAfter\"", writer.toString());
   }

   @Test
   public void testStringWriteEmbeddedFormFeed() throws Exception {
      JSONString str = JSON.string("Before\fAfter");

      StringWriter writer = new StringWriter();
      str.write(writer);
      assertEquals("\"Before\\fAfter\"", writer.toString());
   }

   @Test
   public void testStringWriteEmbeddedEscapeCharacter() throws Exception {
      JSONString str = JSON.string("Before\\After");

      StringWriter writer = new StringWriter();
      str.write(writer);
      assertEquals("\"Before\\\\After\"", writer.toString());
   }



   @Test
   public void testStringWriteWithEmbeddedQuotes() throws Exception {
      JSONString str = JSON.string("{\"name\":\"chris\"}");

      StringWriter writer = new StringWriter();
      str.write(writer);
      assertEquals("\"{\\\"name\\\":\\\"chris\\\"}\"", writer.toString());
   }



   @Test
   public void testMaxBigInteger() {
      JSON.number(BigInteger.valueOf(2).pow(53));
   }

   @Test(expected = ArithmeticException.class)
   public void testTooBigBigInteger() {
      JSON.number(BigInteger.valueOf(2).pow(53).add(BigInteger.ONE));
   }

   @Test
   public void testMinBigInteger() {
      JSON.number(BigInteger.valueOf(2).pow(53).negate());
   }

   @Test(expected = ArithmeticException.class)
   public void testTooSmallBigInteger() {
      JSON.number(BigInteger.valueOf(2).pow(53).add(BigInteger.ONE).negate());
   }


   @Test
   public void testMaxLong() {
      JSON.number(BigInteger.valueOf(2).pow(53).longValue());
   }

   @Test(expected = ArithmeticException.class)
   public void testTooBigLong() {
      JSON.number(BigInteger.valueOf(2).pow(53).add(BigInteger.ONE).longValue());
   }

   @Test
   public void testMinLong() {
      JSON.number(BigInteger.valueOf(2).pow(53).negate().longValue());
   }

   @Test(expected = ArithmeticException.class)
   public void testTooSmallLong() {
      JSON.number(BigInteger.valueOf(2).pow(53).add(BigInteger.ONE).negate().longValue());
   }




   @Test
   public void testEmptyStream() throws Exception {
      InputStream emptyStream = new ByteArrayInputStream(new byte[0]);
      assertNull(JSON.parse(new InputStreamReader(emptyStream, Charset.forName("utf-8"))));
   }

   @Test
   public void testPrematureEnd() throws Exception
   {
      Charset charset = Charset.forName("utf-8");
      for(int i = 1; i <= 27; i++) {
         String resource = String.format("/eof/eof-%02d.json", i);
         try(InputStream in = getClass().getResourceAsStream(resource)) {
            try {
               JSON.parse(new InputStreamReader(in, charset));
               fail("failed on resource: " + resource);
            } catch(SyntaxException e) { /* Test Passes */ }
         }
      }
   }



   private Reader load(String name) throws IOException
   {
      return new InputStreamReader(getClass().getResourceAsStream(name), Charset.forName("UTF-8"));
   }

}
