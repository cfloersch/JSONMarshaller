package org.xpertss.json.desc;

import xpertss.json.JSONBoolean;
import xpertss.json.JSONValue;
import xpertss.json.MarshallingException;

import java.lang.reflect.Array;

import static java.lang.String.format;
import static xpertss.json.JSON.FALSE;
import static xpertss.json.JSON.NULL;
import static xpertss.json.JSON.TRUE;

/**
 * Descriptor for the {@link Boolean} type.
 */
public class BooleanDescriptor extends AbstractDescriptor<Boolean, JSONBoolean> {

   public final static BooleanDescriptor BOOLEAN_DESC = new BooleanDescriptor(Boolean.class);
   public final static BooleanDescriptor BOOLEAN_LITERAL_DESC = new BooleanDescriptor(Boolean.TYPE) {
      @Override
      public JSONValue marshall(FieldDescriptor fieldDescriptor, Object parentEntity, String view)
      {
         return fieldDescriptor.getFieldValueBoolean(parentEntity) ? TRUE : FALSE;
      }

      @Override
      public void unmarshall(FieldDescriptor fieldDescriptor, Object entity, JSONValue marshalled, String view)
      {
         if(NULL.equals(marshalled)) {
            throw new MarshallingException("did not expect null for boolean field");
         } else if(marshalled instanceof JSONBoolean) {
            fieldDescriptor.setFieldValueBoolean(entity, ((JSONBoolean) marshalled).getBoolean());
         } else {
            throw new MarshallingException(format("expected JSONBoolean but found %s", type(marshalled)));
         }
      }

      @Override
      public JSONBoolean marshallArray(Object array, int index, String view)
      {
         return Array.getBoolean(array, index) ? TRUE : FALSE;
      }

      @Override
      public void unmarshallArray(Object array, JSONValue value, int index, String view)
      {
         if(NULL.equals(value)) {
            throw new MarshallingException("did not expect null for boolean field");
         } else if(value instanceof JSONBoolean) {
            Array.setBoolean(array, index, ((JSONBoolean) value).getBoolean());
         } else {
            throw new MarshallingException(format("expected JSONBoolean but found %s", type(value)));
         }
      }

      @Override
      public String toString()
      {
         return getClass().getSuperclass().getSimpleName();
      }
   };

   private BooleanDescriptor(Class<Boolean> klass)
   {
      super(klass);
   }

   public final JSONBoolean marshall(Boolean entity, String view)
   {
      return entity == null ? NULL : entity ? TRUE : FALSE;
   }

   public final Boolean unmarshall(JSONBoolean marshalled, String view)
   {
      return NULL.equals(marshalled) ? null : marshalled.getBoolean();
   }

}
