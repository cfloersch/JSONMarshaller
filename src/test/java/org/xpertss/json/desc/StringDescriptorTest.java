package org.xpertss.json.desc;

import org.junit.Test;
import xpertss.json.JSONString;

import java.lang.reflect.Type;

import static org.junit.Assert.assertEquals;

public class StringDescriptorTest {

   private DescriptorFactory factory = new DescriptorFactory();

   private String testObj = "Hello";

   @Test
   public void testSimple()
   {
      StringDescriptor desc = (StringDescriptor) factory.create((Type) testObj.getClass());
      JSONString value = desc.marshall(testObj, null);
      String result = desc.unmarshall(value, null);
      assertEquals(testObj, result);
   }


}