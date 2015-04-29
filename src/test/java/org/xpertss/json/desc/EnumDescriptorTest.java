package org.xpertss.json.desc;

import org.junit.Test;
import xpertss.json.JSONString;

import java.lang.reflect.Type;

import static org.junit.Assert.assertEquals;

public class EnumDescriptorTest {

   private DescriptorFactory factory = new DescriptorFactory();

   @Test
   public void testSimple()
   {
      Thread.State state = Thread.State.NEW;
      EnumDescriptor<Thread.State> desc = (EnumDescriptor<Thread.State>) factory.create((Type)state.getClass());
      JSONString value = desc.marshall(state, null);
      assertEquals(state, desc.unmarshall(value, null));
   }



}