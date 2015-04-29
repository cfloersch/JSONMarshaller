package org.xpertss.json.desc;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by cfloersch on 6/12/2014.
 */
public class EnumsTest {

   private DescriptorFactory factory = new DescriptorFactory();

   // Enum Tests
   public enum State {
      On, Off
   }


   private State state;
   private List<State> states;

   @Test
   public void testBinaryEnum() throws Exception
   {
      Field field = getClass().getDeclaredField("state");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("EnumDescriptor<State>", desc.toString());
   }

   @Test
   public void testEmbeddedEnum() throws Exception
   {
      Field field = getClass().getDeclaredField("states");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("List<EnumDescriptor<State>>", desc.toString());
   }

}
