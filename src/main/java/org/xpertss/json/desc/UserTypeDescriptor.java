package org.xpertss.json.desc;

import xpertss.json.JSONValue;
import xpertss.json.spi.JSONUserType;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import static xpertss.json.JSON.NULL;

/**
 * Descriptor wrapping user-defined {@link xpertss.json.spi.JSONUserType}s.
 */
public class UserTypeDescriptor<E, J extends JSONValue> extends AbstractDescriptor<E, J> {

   private final JSONUserType<E, J> type;

   public UserTypeDescriptor(JSONUserType<E, J> type)
   {
      super(type.getReturnedClass(), extractJsonType(type.getClass()));
      this.type = type;
   }



   @SuppressWarnings("unchecked")
   public J marshall(E entity, String view)
   {
      if(entity == null) {
         // The JSON.Value hierarchy is closed with Null as the bottom type.
         return (J) NULL;
      } else {
         return type.marshall(getReturnedClass().cast(entity));
      }
   }

   public E unmarshall(J marshalled, String view)
   {
      if(NULL.equals(marshalled)) {
         return null;
      } else {
         return type.unmarshall(marshalled);
      }
   }


   @Override
   @SuppressWarnings("unchecked")
   public Class<E> getReturnedClass()
   {
      return (Class<E>) super.getReturnedClass();
   }


   @Override
   public String toString()
   {
      return getClass().getSimpleName() + "<" + getReturnedClass().getSimpleName() + ">";
   }





   private static Class extractJsonType(Class c)
   {
      Type[] types = c.getGenericInterfaces();
      for(Type type : types) {
         if(type instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) type;
            if(pt.getRawType() == JSONUserType.class) {
               return (Class) pt.getActualTypeArguments()[1];
            }
         }
      }
      throw new IllegalArgumentException("could not extract json type from " + c.getSimpleName());
   }


}
