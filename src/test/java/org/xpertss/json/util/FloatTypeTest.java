package org.xpertss.json.util;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.xpertss.json.util.FloatType.*;

public class FloatTypeTest {

   public static final Long MAX_LONG_AS_DOUBLE = 9007199254740992L;
   public static final Long MIN_LONG_AS_DOUBLE = -9007199254740992L;

   private BigDecimal maxPlusOne = BigDecimal.valueOf(MAX_LONG_AS_DOUBLE).add(BigDecimal.ONE);
   private BigDecimal minMinusOne = BigDecimal.valueOf(MIN_LONG_AS_DOUBLE).subtract(BigDecimal.ONE);


   @Test
   public void testDoubleOverflow()
   {
      BigDecimal dec = BigDecimal.valueOf(MAX_LONG_AS_DOUBLE);
      assertEquals(dec, BigDecimal.valueOf(dec.doubleValue()));
      BigDecimal plus = dec.add(BigDecimal.ONE);
      assertEquals(dec, BigDecimal.valueOf(plus.doubleValue()));
   }

   @Test
   public void testDoubleUnderflow()
   {
      BigDecimal dec = BigDecimal.valueOf(MIN_LONG_AS_DOUBLE);
      assertEquals(dec, BigDecimal.valueOf(dec.doubleValue()));
      BigDecimal minus = dec.subtract(BigDecimal.ONE);
      assertEquals(dec, BigDecimal.valueOf(minus.doubleValue()));
   }



   @Test
   public void testDoubleSuccess()
   {
      Decimal64.checkCast(BigDecimal.ZERO);
      Decimal64.checkCast(BigDecimal.ZERO.negate());
      Decimal64.checkCast(BigDecimal.ONE);
      Decimal64.checkCast(BigDecimal.ONE.negate());
      Decimal64.checkCast(BigDecimal.TEN);
      Decimal64.checkCast(BigDecimal.TEN.negate());
      Decimal64.checkCast(BigDecimal.valueOf(2.2D));

      Decimal32.checkCast(BigDecimal.ZERO);
      Decimal32.checkCast(BigDecimal.ZERO.negate());
      Decimal32.checkCast(BigDecimal.ONE);
      Decimal32.checkCast(BigDecimal.ONE.negate());
      Decimal32.checkCast(BigDecimal.TEN);
      Decimal32.checkCast(BigDecimal.TEN.negate());
      Decimal32.checkCast(BigDecimal.valueOf(2.2D));
   }



   @Test(expected = ArithmeticException.class)
   public void testDoubleOverflowAtMaxPlusOne()
   {
      Decimal64.checkCast(maxPlusOne);
   }

   @Test
   public void testDoubleOverflowAtMaxPlusTwo()
   {
      Decimal64.checkCast(maxPlusOne.add(BigDecimal.ONE));
   }

   @Test(expected = ArithmeticException.class)
   public void testDoubleOverflowAtMaxPlusThree()
   {
      BigDecimal maxPlusTwo = maxPlusOne.add(BigDecimal.ONE);
      Decimal64.checkCast(maxPlusTwo.add(BigDecimal.ONE));
   }



   @Test(expected = ArithmeticException.class)
   public void testDoubleOverflowAtMinMinusOne()
   {
      Decimal64.checkCast(minMinusOne);
   }

   @Test
   public void testDoubleOverflowAtMinMinusTwo()
   {
      Decimal64.checkCast(minMinusOne.subtract(BigDecimal.ONE));
   }

   @Test(expected = ArithmeticException.class)
   public void testDoubleOverflowAtMinMinusThree()
   {
      BigDecimal minMinusTwo = minMinusOne.subtract(BigDecimal.ONE);
      Decimal64.checkCast(minMinusTwo.subtract(BigDecimal.ONE));
   }


   @Test(expected = ArithmeticException.class)
   public void testMaxDouble()
   {
      Decimal32.checkCast(BigDecimal.valueOf(Double.MAX_VALUE));
   }

   @Test(expected = ArithmeticException.class)
   public void testMinDouble()
   {
      Decimal32.checkCast(BigDecimal.valueOf(Double.MIN_VALUE));
   }

   @Test(expected = ArithmeticException.class)
   public void testMinNormalDouble()
   {
      Decimal32.checkCast(BigDecimal.valueOf(Double.MIN_NORMAL));
   }



   @Test
   public void testMaxDoubleSuccess()
   {
      Decimal64.checkCast(BigDecimal.valueOf(Double.MAX_VALUE));
   }

   @Test
   public void testMinDoubleSuccess()
   {
      Decimal64.checkCast(BigDecimal.valueOf(Double.MIN_VALUE));
   }

   @Test
   public void testMinNormalDoubleSuccess()
   {
      Decimal64.checkCast(BigDecimal.valueOf(Double.MIN_NORMAL));
   }



   @Test
   public void testLoad() {
      BigDecimal value = new BigDecimal("0.1");
      for(int i = 0; i < 100000; i++) Decimal64.checkCast(value);
   }


}