/**
 * Created by IntelliJ IDEA.
 * User: cfloersch
 * Date: 4/16/11 11:28 PM
 * Copyright XpertSoftware. All rights reserved.
 */
package org.xpertss.json.desc;

import xpertss.json.Entity;
import xpertss.json.JSON;
import xpertss.json.JSONObject;
import xpertss.json.MarshallingException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import static java.lang.String.format;

public class ConcreteEntityDescriptor<T> extends AbstractDescriptor<T, JSONObject> implements EntityDescriptor<T> {


   private final Set<FieldDescriptor> fields;
   private final Constructor<T> constructor;
   private final Entity annotation;

   public ConcreteEntityDescriptor(Constructor<T> constructor, Set<FieldDescriptor> fields)
   {
      super(constructor.getDeclaringClass());
      this.annotation = getReturnedClass().getAnnotation(Entity.class);
      this.constructor = constructor;
      this.fields = fields;
   }



   @Override
   public String getDiscriminator() { return annotation.discriminator(); }

   @Override
   public Set<FieldDescriptor> getFieldDescriptors()
   {
      return fields;
   }













   @Override
   public JSONObject marshall(T entity, String view)
   {
      if(entity == null) return JSON.NULL;
      JSONObject jsonObject = JSON.object();
      for(FieldDescriptor desc : fields) {
         desc.marshall(entity, view, jsonObject);
      }
      return jsonObject;
   }

   @Override
   public T unmarshall(JSONObject object, String view)
   {
      if(JSON.NULL.equals(object)) return null;
      try {
         T entity = constructor.newInstance();
         for(FieldDescriptor desc : fields) {
            desc.unmarshall(entity, view, object);
         }
         return entity;
      } catch(InstantiationException | InvocationTargetException | IllegalAccessException e) {
         throw new MarshallingException(e);
      }
   }







   @Override
   public String toString()
   {
      return toString(0);
   }

   public String toString(int pad)
   {
      StringBuilder builder = new StringBuilder();
      builder.append(
         format("ConcreteEntityDescriptor<%s> {\n",
            getReturnedClass().getSimpleName()));
      for(FieldDescriptor f : fields) {
         for(int i = 0; i < pad + 2; i++) {
            builder.append(" ");
         }
         builder.append(f.toString(pad + 2) + "\n");
      }
      for(int i = 0; i < pad; i++) {
         builder.append(" ");
      }
      builder.append("}");
      return builder.toString();
   }

}
