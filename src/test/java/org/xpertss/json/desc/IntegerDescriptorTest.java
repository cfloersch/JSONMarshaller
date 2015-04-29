package org.xpertss.json.desc;

import org.junit.Test;
import xpertss.json.JSONNumber;

import java.lang.reflect.Type;

import static org.junit.Assert.*;

public class IntegerDescriptorTest {

   private DescriptorFactory factory = new DescriptorFactory();

   private Integer testObj = 3;
   private int testLiteral = 4;

   @Test
   public void testSimpleObject()
   {
      IntegerDescriptor desc = (IntegerDescriptor) factory.create((Type) testObj.getClass());
      JSONNumber value = desc.marshall(testObj, null);
      Integer result = desc.unmarshall(value, null);
      assertEquals(testObj, result);
   }

   @Test
   public void testSimpleLiteral()
   {
      IntegerDescriptor desc = (IntegerDescriptor) factory.create((Type) Integer.TYPE);
      JSONNumber value = desc.marshall(testLiteral, null);
      int result = desc.unmarshall(value, null);
      assertEquals(testLiteral, result);
   }


}