package org.xpertss.json.desc;

import xpertss.json.JSON;
import xpertss.json.JSONArray;
import xpertss.json.JSONValue;
import xpertss.json.desc.CollectionType;

import java.util.Collection;

import static java.lang.String.format;

/**
 * Descriptor for {@link java.util.Set}s or {@link java.util.List}s.
 */
@SuppressWarnings("unchecked")
public class CollectionDescriptor extends AbstractDescriptor<Collection, JSONArray> {

   private final CollectionType collectionType;
   private final Descriptor<Object, JSONValue> collectionDescriptor;

   public CollectionDescriptor(Class<? extends Collection> collectionClass,
                        Descriptor<? extends Object, ? extends Object> collectionDescriptor)
   {
      super(collectionClass);
      this.collectionDescriptor = (Descriptor<Object, JSONValue>) collectionDescriptor;
      this.collectionType = CollectionType.fromClass(collectionClass);
   }


   public JSONArray marshall(Collection entity, String view)
   {
      if(entity == null) {
         return JSON.NULL;
      } else {
         JSONArray jsonArray = JSON.array();
         for(Object o : entity) {
            jsonArray.add(collectionDescriptor.marshall(o, view));
         }
         return jsonArray;
      }
   }

   public Collection<?> unmarshall(JSONArray jsonArray, String view)
   {
      if(JSON.NULL.equals(jsonArray)) {
         return null;
      } else {
         Collection<Object> collection = collectionType.newCollection();
         for(int i = 0; i < jsonArray.size(); i++) {
            collection.add(collectionDescriptor.unmarshall(jsonArray.get(i), view));
         }
         return collection;
      }
   }


   @Override
   public boolean equals(Object obj)
   {
      if(obj instanceof CollectionDescriptor) {
         CollectionDescriptor that = (CollectionDescriptor) obj;
         return this.collectionDescriptor.equals(that.collectionDescriptor);
      }
      return false;
   }

   @Override
   public int hashCode()
   {
      return collectionDescriptor.hashCode();
   }

   @Override
   public String toString()
   {
      return getReturnedClass().getSimpleName() + "<" + collectionDescriptor + ">";
   }


}
