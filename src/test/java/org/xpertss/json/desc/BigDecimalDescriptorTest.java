package org.xpertss.json.desc;

import org.junit.Test;
import xpertss.json.JSONNumber;

import java.lang.reflect.Type;
import java.math.BigDecimal;

import static org.junit.Assert.*;

public class BigDecimalDescriptorTest {

   private DescriptorFactory factory = new DescriptorFactory();

   private BigDecimal one = BigDecimal.ONE;

   @Test
   public void testSimple()
   {
      BigDecimalDescriptor desc = (BigDecimalDescriptor) factory.create((Type)one.getClass());
      JSONNumber value = desc.marshall(one, null);
      BigDecimal result = desc.unmarshall(value, null);
      assertEquals(one, result);
   }



}