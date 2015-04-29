package org.xpertss.json.desc;

import java.math.BigDecimal;

public class BigDecimalDescriptor extends NumberDescriptor<BigDecimal> {

   public final static BigDecimalDescriptor BIG_DECIMAL_DESC = new BigDecimalDescriptor();

   private BigDecimalDescriptor()
   {
      super(BigDecimal.class);
   }


   @Override
   protected BigDecimal encode(BigDecimal entity)
   {
      return entity;
   }

   @Override
   protected BigDecimal decode(BigDecimal entity)
   {
      return entity;
   }

}
