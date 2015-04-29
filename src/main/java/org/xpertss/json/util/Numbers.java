/**
 * Created By: cfloersch
 * Date: 6/17/2014
 * Copyright 2013 XpertSoftware
 */
package org.xpertss.json.util;


public final class Numbers {

   private Numbers() { }

   public static final Long MAX_LONG_AS_DOUBLE = 9007199254740992L;
   public static final Long MIN_LONG_AS_DOUBLE = -9007199254740992L;


   /**
    * Doubles can only accurately represent integer numbers with its 53 bit mantissa.
    * This method will cast a {@code long} to a {@code double} only if its range falls
    * within the range representable by a double without losing precision.
    * <p/>
    * The acceptable range of a long is -9007199254740992L <= x <= 9007199254740992L
    *
    *
    * @param value any value in the range of the {@code double} type
    * @return the {@code double} value that equals {@code value}
    * @throws ArithmeticException if {@code value} is greater than 9007199254740992L
    *    or less than -9007199254740992L
    */
   public static double safeCast(long value)
   {
      if(MIN_LONG_AS_DOUBLE <= value && value <= MAX_LONG_AS_DOUBLE) return (double) value;
      throw new ArithmeticException("numeric overflow");
   }


}
