package org.xpertss.json.desc;

import org.xpertss.json.util.Numbers;
import xpertss.json.JSONNumber;
import xpertss.json.JSONValue;
import xpertss.json.MarshallingException;

import java.lang.reflect.Array;
import java.math.BigDecimal;

import static java.lang.String.format;
import static xpertss.json.JSON.NULL;
import static xpertss.json.JSON.number;

/**
 * Descriptor for the {@link Long} type.
 */
public class LongDescriptor extends NumberDescriptor<Long> {

   public final static LongDescriptor LONG_DESC = new LongDescriptor(Long.class);
   public final static LongDescriptor LONG_LITERAL_DESC = new LongDescriptor(Long.TYPE) {
      @Override
      public JSONValue marshall(FieldDescriptor fieldDescriptor, Object parentEntity, String view)
      {
         return number(fieldDescriptor.getFieldValueLong(parentEntity));
      }

      @Override
      public void unmarshall(FieldDescriptor fieldDescriptor, Object entity, JSONValue marshalled, String view)
      {
         if(NULL.equals(marshalled)) {
            throw new MarshallingException("did not expect null for long field");
         } else if(marshalled instanceof JSONNumber) {
            JSONNumber number = (JSONNumber) marshalled;
            fieldDescriptor.setFieldValueLong(entity, number.getNumber().longValueExact());
         } else {
            throw new MarshallingException(format("expected JSONNumber but found %s", type(marshalled)));
         }
      }

      @Override
      public JSONNumber marshallArray(Object array, int index, String view)
      {
         return number(Array.getLong(array, index));
      }

      @Override
      public void unmarshallArray(Object array, JSONValue marshalled, int index, String view)
      {
         if(NULL.equals(marshalled)) {
            throw new MarshallingException("did not expect null for long field");
         } else if(marshalled instanceof JSONNumber) {
            JSONNumber number = (JSONNumber) marshalled;
            Array.setLong(array, index, number.getNumber().longValueExact());
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

   private LongDescriptor(Class<Long> klass)
   {
      super(klass);
   }

   @Override
   protected BigDecimal encode(Long entity)
   {
      return BigDecimal.valueOf(entity);
   }

   @Override
   Long decode(BigDecimal entity)
   {
      return entity.longValueExact();
   }

}
