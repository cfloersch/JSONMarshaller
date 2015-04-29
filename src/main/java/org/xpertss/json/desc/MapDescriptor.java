package org.xpertss.json.desc;

import xpertss.json.JSON;
import xpertss.json.JSONObject;
import xpertss.json.JSONString;
import xpertss.json.JSONValue;
import xpertss.json.desc.MapType;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import static xpertss.json.JSON.NULL;

/**
 * Descriptor for {@link java.util.Map}s.
 */
@SuppressWarnings("unchecked")
public final class MapDescriptor extends AbstractDescriptor<Map, JSONObject> {

   private final MapType mapType;
   final Descriptor<Object, JSONValue> valueDescriptor;

   public MapDescriptor(Class<? extends Map> mapClass, Descriptor<?, ?> valueDescriptor)
   {
      super(mapClass);
      this.mapType = MapType.fromClass(mapClass);
      this.valueDescriptor = (Descriptor<Object, JSONValue>) valueDescriptor;
   }


   @Override
   public String toString()
   {
      return getReturnedClass().getSimpleName() + "<" + valueDescriptor + ">";
   }


   public JSONObject marshall(Map entity, String view)
   {
      if(entity == null) {
         return NULL;
      } else {
         Map<String, Object> map = entity;
         JSONObject o = JSON.object();
         for(Entry<String, Object> e : map.entrySet()) {
            o.put(
               JSON.string(e.getKey()),
               valueDescriptor.marshall(e.getValue(), view));
         }
         return o;
      }
   }

   public Map<String, ?> unmarshall(JSONObject object, String view)
   {
      if(NULL.equals(object)) {
         return null;
      } else {
         Map<String, Object> map = mapType.newMap();
         for(Entry<JSONString,JSONValue> entry : object.entrySet()) {
            map.put(entry.getKey().getString(),
                  valueDescriptor.unmarshall(entry.getValue(), view));
         }
         return map;
      }
   }

   Descriptor<Object, JSONValue> getValueDescriptor()
   {
      return valueDescriptor;
   }

}
