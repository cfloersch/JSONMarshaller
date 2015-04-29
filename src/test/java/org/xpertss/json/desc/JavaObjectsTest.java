package org.xpertss.json.desc;

import org.junit.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.Assert.assertEquals;

/**
 * Created by cfloersch on 6/12/2014.
 */
public class JavaObjectsTest {

   private DescriptorFactory factory = new DescriptorFactory();

   // Basic Java Object Tests
   private BigInteger integralObject;
   private BigDecimal decimalObject;
   private String stringObject;
   private Double doubleObject;
   private Float floatObject;
   private Long longObject;
   private Integer integerObject;
   private Short shortObject;
   private Byte byteObject;
   private Character characterObject;
   private Boolean booleanObject;


   @Test
   public void testIntegralObject() throws Exception
   {
      Field field = getClass().getDeclaredField("integralObject");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("BigIntegerDescriptor", desc.toString());
   }

   @Test
   public void testDecimalObject() throws Exception
   {
      Field field = getClass().getDeclaredField("decimalObject");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("BigDecimalDescriptor", desc.toString());
   }

   @Test
   public void testStringObject() throws Exception
   {
      Field field = getClass().getDeclaredField("stringObject");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("StringDescriptor", desc.toString());
   }

   @Test
   public void testDoubleObject() throws Exception
   {
      Field field = getClass().getDeclaredField("doubleObject");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("DoubleDescriptor", desc.toString());
   }

   @Test
   public void testFloatObject() throws Exception
   {
      Field field = getClass().getDeclaredField("floatObject");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("FloatDescriptor", desc.toString());
   }

   @Test
   public void testLongObject() throws Exception
   {
      Field field = getClass().getDeclaredField("longObject");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("LongDescriptor", desc.toString());
   }

   @Test
   public void testIntegerObject() throws Exception
   {
      Field field = getClass().getDeclaredField("integerObject");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("IntegerDescriptor", desc.toString());
   }

   @Test
   public void testShortObject() throws Exception
   {
      Field field = getClass().getDeclaredField("shortObject");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("ShortDescriptor", desc.toString());
   }

   @Test
   public void testByteObject() throws Exception
   {
      Field field = getClass().getDeclaredField("byteObject");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("ByteDescriptor", desc.toString());
   }

   @Test
   public void testCharacterObject() throws Exception
   {
      Field field = getClass().getDeclaredField("characterObject");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("CharacterDescriptor", desc.toString());
   }

   @Test
   public void testBooleanObject() throws Exception
   {
      Field field = getClass().getDeclaredField("booleanObject");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("BooleanDescriptor", desc.toString());
   }

}
