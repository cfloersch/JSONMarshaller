package org.xpertss.json.desc;

import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;

/**
 * Created by cfloersch on 6/12/2014.
 */
public class ArraysTest {

   private DescriptorFactory factory = new DescriptorFactory();

   // Array Tests
   private String[] singleArray;
   private String[][] doubleArray;
   private String[][][] tripleArray;


   @Test
   public void testSingleArray() throws Exception
   {
      Field field = getClass().getDeclaredField("singleArray");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("StringDescriptor[]", desc.toString());
   }

   @Test
   public void testDoubleArray() throws Exception
   {
      Field field = getClass().getDeclaredField("doubleArray");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("StringDescriptor[][]", desc.toString());
   }

   @Test
   public void testTripleArray() throws Exception
   {
      Field field = getClass().getDeclaredField("tripleArray");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("StringDescriptor[][][]", desc.toString());
   }

}
