package org.xpertss.json.desc;

import xpertss.json.*;
import org.xpertss.json.util.Generics;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import static java.lang.String.format;

/**
 * Abstract implementation of {@link Descriptor} providing common
 * functionality.
 */
public abstract class AbstractDescriptor<E, J extends JSONValue> implements Descriptor<E, J> {

   private final Class<?> klass;
   private final Class jsonType;

   /**
    * Default constructor.
    *
    * @param klass the returned class of this descriptor, see
    *              {@link #getReturnedClass()}
    */
   public AbstractDescriptor(Class<? extends E> klass)
   {
      this.klass = klass;
      this.jsonType = extractJsonType(getClass());
   }

   public AbstractDescriptor(Class<? extends E> klass, Class jsonType)
   {
      this.klass = klass;
      this.jsonType = jsonType;
   }

   public Class<?> getReturnedClass()
   {
      return klass;
   }




   @SuppressWarnings("unchecked")
   public JSONValue marshall(FieldDescriptor fieldDescriptor, Object parentEntity, String view)
   {
      return marshall((E) fieldDescriptor.getFieldValue(parentEntity), view);
   }

   @SuppressWarnings("unchecked")
   public void unmarshall(FieldDescriptor fieldDescriptor, Object entity, JSONValue marshalled, String view)
   {
      if(jsonType.isAssignableFrom(marshalled.getClass())) {
         fieldDescriptor.setFieldValue(entity, unmarshall((J)marshalled, view));
      } else {
         throw new MarshallingException(format("expected %s but found %s", jsonType.getSimpleName(), type(marshalled)));
      }
   }





   @SuppressWarnings("unchecked")
   public J marshallArray(Object array, int index, String view)
   {
      return marshall((E) Array.get(array, index), view);
   }



   @SuppressWarnings("unchecked")
   public void unmarshallArray(Object array, JSONValue value, int index, String view)
   {
      if(jsonType.isAssignableFrom(value.getClass())) {
         Array.set(array, index, unmarshall((J) value, view));
      } else {
         throw new MarshallingException(format("expected %s but found %s", jsonType.getSimpleName(), type(value)));
      }
   }




   @Override
   public boolean equals(Object object)
   {
      if(object instanceof Descriptor) {
         Descriptor<?, ?> that = (Descriptor<?, ?>) object;
         return this.getReturnedClass().equals(that.getReturnedClass());
      }
      return false;
   }

   @Override
   public int hashCode()
   {
      return this.getReturnedClass().hashCode();
   }

   public String toString(int pad)
   {
      StringBuilder builder = new StringBuilder();
      builder.append(toString());
      return builder.toString();
   }

   @Override
   public String toString()
   {
      return getClass().getSimpleName();
   }



   protected String type(JSONValue value)
   {
      if(value instanceof JSONString) {
         return "JSONString";
      } else if(value instanceof JSONNumber) {
         return "JSONNumber";
      } else if(value instanceof JSONBoolean) {
         return "JSONBoolean";
      } else if(value instanceof JSONArray) {
         return "JSONArray";
      } else if(value instanceof JSONObject) {
         return "JSONObject";
      }
      return "Unknown";
   }

   private Class extractJsonType(Class c)
   {
      Type type = Generics.resolveSuperTypes(c);
      while(type != Object.class) {
         if(type instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) type;
            if (pt.getRawType() == AbstractDescriptor.class) {
               return (Class) pt.getActualTypeArguments()[1];
            }
            type = Generics.resolveSuperTypes(pt);
         } else {
            type = Generics.resolveSuperTypes(type);
         }
      }
      throw new IllegalArgumentException("could not extract json type from " + c.getSimpleName());
   }


}
