package org.xpertss.json.util;

import org.junit.Test;

public class NumbersTest {

   @Test
   public void testMaxLongAsDouble()
   {
      Numbers.safeCast(Numbers.MAX_LONG_AS_DOUBLE);
   }

   @Test(expected = ArithmeticException.class)
   public void testMaxLong()
   {
      Numbers.safeCast(Long.MAX_VALUE);
   }

   @Test(expected = ArithmeticException.class)
   public void testMaxLongPlusOne()
   {
      Numbers.safeCast(Numbers.MAX_LONG_AS_DOUBLE + 1L);
   }

   @Test
   public void testMinLongAsDouble()
   {
      Numbers.safeCast(Numbers.MIN_LONG_AS_DOUBLE);
   }

   @Test(expected = ArithmeticException.class)
   public void testMinLong()
   {
      Numbers.safeCast(Long.MIN_VALUE);
   }

   @Test(expected = ArithmeticException.class)
   public void testMinLongMinusOne()
   {
      Numbers.safeCast(Numbers.MIN_LONG_AS_DOUBLE -1L);
   }


}