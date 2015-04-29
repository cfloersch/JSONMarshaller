package org.xpertss.json.desc;

import org.junit.Test;
import xpertss.json.JSONBoolean;

import java.lang.reflect.Type;

import static org.junit.Assert.assertEquals;

public class BooleanDescriptorTest {

   private DescriptorFactory factory = new DescriptorFactory();

   @Test
   public void testSimpleObject()
   {
      Boolean testObj = Boolean.TRUE;
      BooleanDescriptor desc = BooleanDescriptor.BOOLEAN_DESC;
      JSONBoolean value = desc.marshall(testObj, null);
      assertEquals(testObj, desc.unmarshall(value, null));
   }



}