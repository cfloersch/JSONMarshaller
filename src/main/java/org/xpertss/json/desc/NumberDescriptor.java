package org.xpertss.json.desc;

import xpertss.json.JSONNumber;

import java.math.BigDecimal;

import static xpertss.json.JSON.NULL;
import static xpertss.json.JSON.number;

/**
 * Abstract descriptor for subtypes of {@link Number}s.
 *
 * @param <N> the type of number
 */
abstract class NumberDescriptor<N extends Number> extends AbstractDescriptor<N, JSONNumber> {

   public NumberDescriptor(Class<? extends N> klass)
   {
      super(klass);
   }



   public final JSONNumber marshall(N entity, String view)
   {
      return entity == null ? NULL : number(encode(entity));
   }

   public final N unmarshall(JSONNumber entity, String view)
   {
      return NULL.equals(entity) ? null : decode(entity.getNumber());
   }


   /**
    * Converts an {@code N} into a {@link java.math.BigDecimal}. The default implementation
    * uses {@link Number#doubleValue()}.
    */
   abstract  BigDecimal encode(N entity);

   /**
    * Convert a {@link java.math.BigDecimal} into an {@code N}.
    */
   abstract N decode(BigDecimal entity);



}
