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
 * Descriptor for the {@link Byte} type.
 */
public class ByteDescriptor extends NumberDescriptor<Byte> {

   public final static ByteDescriptor BYTE_DESC = new ByteDescriptor(Byte.class);
   public final static ByteDescriptor BYTE_LITERAL_DESC = new ByteDescriptor(Byte.TYPE) {
      @Override
      public JSONValue marshall(FieldDescriptor fieldDescriptor, Object parentEntity, String view)
      {
         return number(fieldDescriptor.getFieldValueByte(parentEntity));
      }

      @Override
      public void unmarshall(FieldDescriptor fieldDescriptor, Object entity, JSONValue marshalled, String view)
      {
         if(NULL.equals(marshalled)) {
            throw new MarshallingException("did not expect null for byte field");
         } else if(marshalled instanceof JSONNumber) {
            JSONNumber number = (JSONNumber) marshalled;
            fieldDescriptor.setFieldValueByte(entity, number.getNumber().byteValueExact());
         } else {
            throw new MarshallingException(format("expected JSONNumber but found %s", type(marshalled)));
         }
      }

      @Override
      public JSONNumber marshallArray(Object array, int index, String view)
      {
         return number(Array.getByte(array, index));
      }

      @Override
      public void unmarshallArray(Object array, JSONValue value, int index, String view)
      {
         if(NULL.equals(value)) {
            throw new MarshallingException("did not expect null for byte field");
         } else if(value instanceof JSONNumber) {
            JSONNumber number = (JSONNumber) value;
            Array.setByte(array, index, number.getNumber().byteValueExact());
         } else {
            throw new MarshallingException(format("expected JSONNumber but found %s", type(value)));
         }
      }
      @Override
      public String toString()
      {
         return getClass().getSuperclass().getSimpleName();
      }
   };


   private ByteDescriptor(Class<Byte> klass)
   {
      super(klass);
   }

   @Override
   protected BigDecimal encode(Byte entity)
   {
      return new BigDecimal(entity.toString());
   }

   @Override
   Byte decode(BigDecimal entity)
   {
      return entity.byteValueExact();
   }


}
