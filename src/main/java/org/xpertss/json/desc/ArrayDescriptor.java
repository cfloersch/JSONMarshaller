package org.xpertss.json.desc;

import xpertss.json.JSON;
import xpertss.json.JSONArray;
import xpertss.json.JSONValue;

import java.lang.reflect.Array;


/**
 * A descriptor for arrays.
 */
public class ArrayDescriptor extends AbstractDescriptor<Object, JSONArray> {

   private final Descriptor<Object, JSONValue> elementsDescriptor;

   @SuppressWarnings("unchecked")
   public ArrayDescriptor(Descriptor elementsDescriptor)
   {
      super(Array.class);
      this.elementsDescriptor = elementsDescriptor;
   }


   public JSONArray marshall(Object entity, String view)
   {
      if(entity == null) return JSON.NULL;
      JSONArray jsonArray = JSON.array();
      int l = Array.getLength(entity);
      for(int i = 0; i < l; i++) {
         jsonArray.add(elementsDescriptor.marshallArray(entity, i, view));
      }
      return jsonArray;
   }

   public Object unmarshall(JSONArray jsonArray, String view)
   {
      if(JSON.NULL.equals(jsonArray)) return null;
      Object array = Array.newInstance(
         elementsDescriptor.getReturnedClass(),
         jsonArray.size());
      int length = Array.getLength(array);
      for(int i = 0; i < length; i++) {
         elementsDescriptor.unmarshallArray(array, jsonArray.get(i), i, view);
      }
      return array;
   }





   @Override
   public String toString()
   {
      return elementsDescriptor + "[]";
   }
}