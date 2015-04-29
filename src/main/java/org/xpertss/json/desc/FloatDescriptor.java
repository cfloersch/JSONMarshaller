package org.xpertss.json.desc;

import org.xpertss.json.util.FloatType;
import xpertss.json.JSONNumber;
import xpertss.json.JSONValue;
import xpertss.json.MarshallingException;

import java.lang.reflect.Array;
import java.math.BigDecimal;

import static java.lang.String.format;
import static xpertss.json.JSON.NULL;
import static xpertss.json.JSON.number;

/**
 * Descriptor for the {@link Float} type.
 */
public class FloatDescriptor extends NumberDescriptor<Float> {

   public final static FloatDescriptor FLOAT_DESC = new FloatDescriptor(Float.class);
   public final static FloatDescriptor FLOAT_LITERAL_DESC = new FloatDescriptor(Float.TYPE) {
      @Override
      public JSONValue marshall(FieldDescriptor fieldDescriptor, Object parentEntity, String view)
      {
         return number(fieldDescriptor.getFieldValueFloat(parentEntity));
      }

      @Override
      public void unmarshall(FieldDescriptor fieldDescriptor, Object entity, JSONValue marshalled, String view)
      {
         if(NULL.equals(marshalled)) {
            throw new MarshallingException("did not expect null for float field");
         } else if(marshalled instanceof JSONNumber) {
            JSONNumber number = (JSONNumber) marshalled;
            fieldDescriptor.setFieldValueFloat(entity, decode(number.getNumber()));
         } else {
            throw new MarshallingException(format("expected JSONNumber but found %s", type(marshalled)));
         }
      }

      @Override
      public JSONNumber marshallArray(Object array, int index, String view)
      {
         return number(Array.getFloat(array, index));
      }

      @Override
      public void unmarshallArray(Object array, JSONValue marshalled, int index, String view)
      {
         if(NULL.equals(marshalled)) {
            throw new MarshallingException("did not expect null for float field");
         } else if(marshalled instanceof JSONNumber) {
            JSONNumber number = (JSONNumber) marshalled;
            Array.setFloat(array, index, decode(number.getNumber()));
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

   private FloatDescriptor(Class<Float> klass)
   {
      super(klass);
   }

   @Override
   protected BigDecimal encode(Float entity)
   {
      return new BigDecimal(entity.toString());
   }

   @Override
   Float decode(BigDecimal entity)
   {
      return FloatType.Decimal32.checkCast(entity).floatValue();
   }

}
