package org.xpertss.json.desc;

import xpertss.json.JSONString;

import static xpertss.json.JSON.NULL;
import static xpertss.json.JSON.string;

/**
 * Descriptor for the {@link Enum} type.
 */
@SuppressWarnings("unchecked")
public class EnumDescriptor<T extends Enum> extends AbstractDescriptor<Enum, JSONString> {

   private final Class<T> enumClass;

   public EnumDescriptor(Class<T> c)
   {
      super(Enum.class);
      enumClass = c;
   }



   public final JSONString marshall(Enum entity, String view)
   {
      return entity == null ? NULL : string(entity.name());
   }

   public final T unmarshall(JSONString marshalled, String view)
   {
      return NULL.equals(marshalled) ? (T) null : (T) Enum.valueOf(enumClass, marshalled.getString());
   }

   @Override
   public String toString()
   {
      return getClass().getSimpleName() + "<" + enumClass.getSimpleName() + ">";
   }

}
