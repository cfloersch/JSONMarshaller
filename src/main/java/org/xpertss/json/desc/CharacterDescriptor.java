package org.xpertss.json.desc;

import xpertss.json.JSONString;
import xpertss.json.JSONValue;
import xpertss.json.MarshallingException;

import java.lang.reflect.Array;

import static java.lang.String.format;
import static xpertss.json.JSON.NULL;
import static xpertss.json.JSON.string;

/**
 * Descriptor for the {@link Character} type.
 */
public class CharacterDescriptor extends AbstractDescriptor<Character, JSONString> {

   public final static CharacterDescriptor CHARARACTER_DESC = new CharacterDescriptor(Character.class);
   public final static CharacterDescriptor CHAR_LITERAL_DESC = new CharacterDescriptor(Character.TYPE) {
      @Override
      public JSONValue marshall(FieldDescriptor fieldDescriptor, Object parentEntity, String view)
      {
         return string(Character.toString(fieldDescriptor.getFieldValueChar(parentEntity)));
      }

      @Override
      public void unmarshall(FieldDescriptor fieldDescriptor, Object entity, JSONValue marshalled, String view)
      {
         if(NULL.equals(marshalled)) {
            throw new MarshallingException("did not expect null for char field");
         } else if(marshalled instanceof JSONString) {
            JSONString string = (JSONString) marshalled;
            if(string.getString().length() == 1) {
               fieldDescriptor.setFieldValueChar(entity, string.getString().charAt(0));
            } else {
               throw new MarshallingException("expected a character but found a string");
            }
         } else {
            throw new MarshallingException(format("expected JSONString but found %s", type(marshalled)));
         }
      }

      @Override
      public JSONString marshallArray(Object array, int index, String view)
      {
         return string(Character.toString(Array.getChar(array, index)));
      }

      @Override
      public void unmarshallArray(Object array, JSONValue value, int index, String view)
      {
         if(NULL.equals(value)) {
            throw new MarshallingException("did not expect null for char field");
         } else if(value instanceof JSONString) {
            JSONString string = (JSONString) value;
            if(string.getString().length() == 1) {
               Array.setChar(array, index, string.getString().charAt(0));
            } else {
               throw new MarshallingException("expected a character but found a string");
            }
         } else {
            throw new MarshallingException(format("expected JSONString but found %s", type(value)));
         }
      }

      @Override
      public String toString()
      {
         return getClass().getSuperclass().getSimpleName();
      }
   };

   private CharacterDescriptor(Class<Character> klass)
   {
      super(klass);
   }



   public final JSONString marshall(Character entity, String view)
   {
      return entity == null ? NULL : string(entity.toString());
   }

   public final Character unmarshall(JSONString marshalled, String view)
   {
      if(NULL.equals(marshalled)) return null;
      if(marshalled.getString().length() == 1) {
         return marshalled.getString().charAt(0);
      } else {
         throw new MarshallingException("expected a character but found a string");
      }
   }


}
