package org.xpertss.json.desc;

import xpertss.json.JSONNumber;
import xpertss.json.JSONValue;
import xpertss.json.MarshallingException;

import java.lang.reflect.Array;
import java.math.BigDecimal;

import static java.lang.String.format;
import static xpertss.json.JSON.NULL;
import static xpertss.json.JSON.number;

/**
 * Descriptor for the {@link Short} type.
 */
public class ShortDescriptor extends NumberDescriptor<Short> {

   public final static ShortDescriptor SHORT_DESC = new ShortDescriptor(Short.class);
   public final static ShortDescriptor SHORT_LITERAL_DESC = new ShortDescriptor(Short.TYPE) {
      @Override
      public JSONValue marshall(FieldDescriptor fieldDescriptor, Object parentEntity, String view)
      {
         return number(fieldDescriptor.getFieldValueShort(parentEntity));
      }

      @Override
      public void unmarshall(FieldDescriptor fieldDescriptor, Object entity, JSONValue marshalled, String view)
      {
         if(NULL.equals(marshalled)) {
            throw new MarshallingException("did not expect null for short field");
         } else if(marshalled instanceof JSONNumber) {
            JSONNumber number = (JSONNumber) marshalled;
            fieldDescriptor.setFieldValueShort(entity, number.getNumber().shortValueExact());
         } else {
            throw new MarshallingException(format("expected JSONNumber but found %s", type(marshalled)));
         }
      }

      @Override
      public JSONNumber marshallArray(Object array, int index, String view)
      {
         return number(Array.getShort(array, index));
      }

      @Override
      public void unmarshallArray(Object array, JSONValue marshalled, int index, String view)
      {
         if(NULL.equals(marshalled)) {
            throw new MarshallingException("did not expect null for short field");
         } else if(marshalled instanceof JSONNumber) {
            JSONNumber number = (JSONNumber) marshalled;
            Array.setShort(array, index, number.getNumber().shortValueExact());
         } else {
            throw new MarshallingException(format("expected JSONNumber but found %s", type(marshalled)));
         }
      }

      @Override
      public String toString()
      {
         return getClass().getSuperclass().getSimpleName();
      }
   };

   private ShortDescriptor(Class<Short> klass)
   {
      super(klass);
   }

   @Override
   protected BigDecimal encode(Short entity)
   {
      return BigDecimal.valueOf(entity);
   }

   @Override
   Short decode(BigDecimal entity)
   {
      return entity.shortValueExact();
   }

}
