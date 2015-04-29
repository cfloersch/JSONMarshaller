package org.xpertss.json.desc;

import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;

/**
 * Created by cfloersch on 6/12/2014.
 */
public class JavaLiteralsTest {

   private DescriptorFactory factory = new DescriptorFactory();



   // Java literals Tests
   private double doubleLiteral;
   private float floatLiteral;
   private long longLiteral;
   private int integerLiteral;
   private short shortLiteral;
   private byte byteLiteral;
   private char characterLiteral;
   private boolean booleanLiteral;

   @Test
   public void testDoubleLiteral() throws Exception
   {
      Field field = getClass().getDeclaredField("doubleLiteral");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("DoubleDescriptor", desc.toString());
   }

   @Test
   public void testFloatLiteral() throws Exception
   {
      Field field = getClass().getDeclaredField("floatLiteral");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("FloatDescriptor", desc.toString());
   }

   @Test
   public void testLongLiteral() throws Exception
   {
      Field field = getClass().getDeclaredField("longLiteral");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("LongDescriptor", desc.toString());
   }

   @Test
   public void testIntegerLiteral() throws Exception
   {
      Field field = getClass().getDeclaredField("integerLiteral");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("IntegerDescriptor", desc.toString());
   }

   @Test
   public void testShortLiteral() throws Exception
   {
      Field field = getClass().getDeclaredField("shortLiteral");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("ShortDescriptor", desc.toString());
   }

   @Test
   public void testByteLiteral() throws Exception
   {
      Field field = getClass().getDeclaredField("byteLiteral");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("ByteDescriptor", desc.toString());
   }

   @Test
   public void testCharacterLiteral() throws Exception
   {
      Field field = getClass().getDeclaredField("characterLiteral");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("CharacterDescriptor", desc.toString());
   }

   @Test
   public void testBooleanLiteral() throws Exception
   {
      Field field = getClass().getDeclaredField("booleanLiteral");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("BooleanDescriptor", desc.toString());
   }


}
