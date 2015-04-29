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
 * Descriptor for the {@link Double} type.
 */
public class DoubleDescriptor extends NumberDescriptor<Double> {

   public final static DoubleDescriptor DOUBLE_DESC = new DoubleDescriptor(Double.class);

   public final static DoubleDescriptor DOUBLE_LITERAL_DESC = new DoubleDescriptor(Double.TYPE) {
      @Override
      public JSONValue marshall(FieldDescriptor fieldDescriptor, Object parentEntity, String view)
      {
         return number(fieldDescriptor.getFieldValueDouble(parentEntity));
      }

      @Override
      public void unmarshall(FieldDescriptor fieldDescriptor, Object entity, JSONValue marshalled, String view)
      {
         if(NULL.equals(marshalled)) {
            throw new MarshallingException("did not expect null for double field");
         } else if(marshalled instanceof JSONNumber) {
            JSONNumber number = (JSONNumber) marshalled;
            fieldDescriptor.setFieldValueDouble(entity, number.getNumber().doubleValue());
         } else {
            throw new MarshallingException(format("expected JSONNumber but found %s", type(marshalled)));
         }
      }

      @Override
      public JSONNumber marshallArray(Object array, int index, String view)
      {
         return number(Array.getDouble(array, index));
      }

      @Override
      public void unmarshallArray(Object array, JSONValue marshalled, int index, String view)
      {
         if(NULL.equals(marshalled)) {
            throw new MarshallingException("did not expect null for double field");
         } else if(marshalled instanceof JSONNumber) {
            JSONNumber number = (JSONNumber) marshalled;
            Array.setDouble(array, index, number.getNumber().doubleValue());
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

   private DoubleDescriptor(Class<Double> klass)
   {
      super(klass);
   }


   @Override
   BigDecimal encode(Double entity)
   {
      return BigDecimal.valueOf(entity.doubleValue());
   }

   @Override
   Double decode(BigDecimal number)
   {
      return number.doubleValue();
   }

}
