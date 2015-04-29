package org.xpertss.json.util;

import java.math.BigDecimal;

/**
 * Created by cfloersch on 6/19/2014.
 */
public enum FloatType {

   Decimal32(23, 8, 7) {

      @Override
      public BigDecimal checkCast(BigDecimal value)
      {
         float val = value.floatValue();
         if(val != Float.POSITIVE_INFINITY && val != Float.NEGATIVE_INFINITY) {
            if(new BigDecimal(Float.toString(val)).compareTo(value) == 0) {
               return value;
            }
         }
         throw new ArithmeticException("loss of precision");
      }

   }, Decimal64(52, 11, 16) {

      @Override
      public BigDecimal checkCast(BigDecimal value)
      {
         double val = value.doubleValue();
         if(val != Double.POSITIVE_INFINITY && val != Double.NEGATIVE_INFINITY) {
            if(new BigDecimal(Double.toString(val)).compareTo(value) == 0) {
               return value;
            }
         }
         throw new ArithmeticException("loss of precision");
      }

   };


   private final int digits;
   private final int precision;
   private final int exponent;

   private FloatType(int precision, int exponent, int digits)
   {
      this.digits = digits;
      this.exponent = exponent;
      this.precision = precision;
   }


   public int getDecimalDigits() { return digits; }

   public int getPrecision() { return precision; }

   public int getExponentSize() { return exponent; }


   public abstract BigDecimal checkCast(BigDecimal value);


}
