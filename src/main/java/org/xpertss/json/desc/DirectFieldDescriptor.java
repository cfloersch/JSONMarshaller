/**
 * Created by IntelliJ IDEA.
 * User: cfloersch
 * Date: 4/16/11 2:40 PM
 * Copyright XpertSoftware. All rights reserved.
 */
package org.xpertss.json.desc;

import xpertss.json.*;
import org.xpertss.json.util.Strings;

import java.lang.reflect.Field;

import static java.lang.String.format;
import static xpertss.json.JSON.string;

public class DirectFieldDescriptor implements FieldDescriptor {

   private Field field;
   private Value value;
   private JSONString name;
   private Descriptor descriptor;

   public DirectFieldDescriptor(Field field, Descriptor descriptor)
   {
      this.field = field;
      this.descriptor = descriptor;
      this.value = field.getAnnotation(Value.class);
      this.name = string(Strings.ifEmpty(value.name(), field.getName()));
      field.setAccessible(true);
   }







   public void marshall(Object entity, String view, JSONObject jsonObject)
   {
      if(isInView(view)) {
         if(isOptional() && getFieldValue(entity) == null) return;
         jsonObject.put(name, descriptor.marshall(this, entity, view));
      }
   }

   public void unmarshall(Object entity, String view, JSONObject jsonObject)
   {
      if(isInView(view)) {
         if (jsonObject.containsKey(name)) {
            descriptor.unmarshall(this, entity, jsonObject.get(name), view);
         } else if (!isOptional()) {
            if (view == null) {
               throw new MarshallingException(
                     format("The field %s has no value.", getFieldName()));
            } else {
               throw new MarshallingException(
                     format("The field %s in the view %s has no value.", getFieldName(), view));
            }
         }
      }
   }





   public JSONString getFieldName()
   {
      return name;
   }




   public Object getFieldValue(Object instance)
   {
      try {
         return field.get(instance);
      } catch(IllegalAccessException e) {
         throw createException(instance);
      }
   }

   public byte getFieldValueByte(Object instance)
   {
      try {
         return field.getByte(instance);
      } catch(IllegalAccessException e) {
         throw createException(instance);
      }
   }

   public char getFieldValueChar(Object instance)
   {
      try {
         return field.getChar(instance);
      } catch(IllegalAccessException e) {
         throw createException(instance);
      }
   }

   public boolean getFieldValueBoolean(Object instance)
   {
      try {
         return field.getBoolean(instance);
      } catch(IllegalAccessException e) {
         throw createException(instance);
      }
   }

   public short getFieldValueShort(Object instance)
   {
      try {
         return field.getShort(instance);
      } catch(IllegalAccessException e) {
         throw createException(instance);
      }
   }

   public int getFieldValueInt(Object instance)
   {
      try {
         return field.getInt(instance);
      } catch(IllegalAccessException e) {
         throw createException(instance);
      }
   }

   public long getFieldValueLong(Object instance)
   {
      try {
         return field.getLong(instance);
      } catch(IllegalAccessException e) {
         throw createException(instance);
      }
   }

   public float getFieldValueFloat(Object instance)
   {
      try {
         return field.getFloat(instance);
      } catch(IllegalAccessException e) {
         throw createException(instance);
      }
   }

   public double getFieldValueDouble(Object instance)
   {
      try {
         return field.getDouble(instance);
      } catch(IllegalAccessException e) {
         throw createException(instance);
      }
   }

   public void setFieldValue(Object instance, Object value)
   {
      try {
         field.set(instance, value);
      } catch(IllegalAccessException e) {
         throw createException(instance);
      }
   }

   public void setFieldValueByte(Object instance, byte value)
   {
      try {
         field.setByte(instance, value);
      } catch(IllegalAccessException e) {
         throw createException(instance);
      }
   }

   public void setFieldValueChar(Object instance, char value)
   {
      try {
         field.setChar(instance, value);
      } catch(IllegalAccessException e) {
         throw createException(instance);
      }
   }

   public void setFieldValueBoolean(Object instance, boolean value)
   {
      try {
         field.setBoolean(instance, value);
      } catch(IllegalAccessException e) {
         throw createException(instance);
      }
   }

   public void setFieldValueShort(Object instance, short value)
   {
      try {
         field.setShort(instance, value);
      } catch(IllegalAccessException e) {
         throw createException(instance);
      }
   }

   public void setFieldValueInt(Object instance, int value)
   {
      try {
         field.setInt(instance, value);
      } catch(IllegalAccessException e) {
         throw createException(instance);
      }
   }

   public void setFieldValueLong(Object instance, long value)
   {
      try {
         field.setLong(instance, value);
      } catch(IllegalAccessException e) {
         throw createException(instance);
      }
   }

   public void setFieldValueFloat(Object instance, float value)
   {
      try {
         field.setFloat(instance, value);
      } catch(IllegalAccessException e) {
         throw createException(instance);
      }
   }

   public void setFieldValueDouble(Object instance, double value)
   {
      try {
         field.set(instance, value);
      } catch(IllegalAccessException e) {
         throw createException(instance);
      }
   }





   public boolean isInView(String view)
   {
      if(view == null) return true;
      for(String v : value.views()) {
         if(view.equals(v)) return true;
      }
      return false;
   }

   public boolean isOptional()
   {
      return value.optional();
   }






   @Override
   public boolean equals(Object obj)
   {
      if(obj instanceof DirectFieldDescriptor) {
         DirectFieldDescriptor that = (DirectFieldDescriptor) obj;
         return name.equals(that.name);
      }
      return false;
   }

   @Override
   public int hashCode()
   {
      return name.hashCode();
   }

   @Override
   public String toString()
   {
      return toString(0);
   }

   public String toString(int pad)
   {
      StringBuilder builder = new StringBuilder();
      for(int i = 0; i < pad; i++) builder.append(" ");
      builder.append(format("%s as \"%s\": ", field.getName(), getFieldName().getString()));
      builder.append(descriptor.toString(pad));
      return builder.toString();
   }



   private RuntimeException createException(Object instance)
   {
      throw new MarshallingException(format("cannot access %s in %s", field.getName(), instance.getClass().getName()));
   }

}
