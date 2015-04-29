/**
 * Created by IntelliJ IDEA.
 * User: cfloersch
 * Date: 4/14/11 12:54 PM
 * Copyright Manheim online
 */
package org.xpertss.json.desc;

import java.math.BigDecimal;
import java.math.BigInteger;

public class BigIntegerDescriptor extends NumberDescriptor<BigInteger> {

   public final static BigIntegerDescriptor BIG_INTEGER_DESC = new BigIntegerDescriptor();


   private BigIntegerDescriptor()
   {
      super(BigInteger.class);
   }

   @Override
   protected BigDecimal encode(BigInteger entity)
   {
      return new BigDecimal(entity);
   }

   @Override
   BigInteger decode(BigDecimal entity)
   {
      return entity.toBigIntegerExact();
   }


}
