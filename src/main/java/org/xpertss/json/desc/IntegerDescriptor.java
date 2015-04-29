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
 * Descriptor for the {@link Integer} type.
 */
public class IntegerDescriptor extends NumberDescriptor<Integer> {

   public final static IntegerDescriptor INTEGER_DESC = new IntegerDescriptor(Integer.class);
   public final static IntegerDescriptor INT_LITERAL_DESC = new IntegerDescriptor(Integer.TYPE) {
      @Override
      public JSONValue marshall(FieldDescriptor fieldDescriptor, Object parentEntity, String view)
      {
         return number(fieldDescriptor.getFieldValueInt(parentEntity));
      }

      @Override
      public void unmarshall(FieldDescriptor fieldDescriptor, Object entity, JSONValue marshalled, String view)
      {
         if(NULL.equals(marshalled)) {
            throw new MarshallingException("did not expect null for int field");
         } else if(marshalled instanceof JSONNumber) {
            JSONNumber number = (JSONNumber) marshalled;
            fieldDescriptor.setFieldValueInt(entity, number.getNumber().intValueExact());
         } else {
            throw new MarshallingException(format("expected JSONNumber but found %s", type(marshalled)));
         }
      }

      @Override
      public JSONNumber marshallArray(Object array, int index, String view)
      {
         return number(Array.getInt(array, index));
      }

      @Override
      public void unmarshallArray(Object array, JSONValue marshalled, int index, String view)
      {
         if(NULL.equals(marshalled)) {
            throw new MarshallingException("did not expect null for int field");
         } else if(marshalled instanceof JSONNumber) {
            JSONNumber number = (JSONNumber) marshalled;
            Array.setInt(array, index, number.getNumber().intValueExact());
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

   private IntegerDescriptor(Class<Integer> klass)
   {
      super(klass);
   }

   @Override
   protected BigDecimal encode(Integer entity)
   {
      return BigDecimal.valueOf(entity);
   }

   @Override
   Integer decode(BigDecimal entity)
   {
      return entity.intValueExact();
   }

}
