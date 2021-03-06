/*
 * Created by IntelliJ IDEA.
 * User: cfloersch
 * Date: 4/8/11 9:49 AM
 * Copyright Manheim online
 */
package xpertss.json;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * An object, i.e. {@code {"hello":"world"}}.
 */
public interface JSONObject extends JSONValue {

   /**
    * Set the named property on this object to the specified value.
    *
    * @param key the property name
    * @param value the property value
    * @return the newly created JSONValue
    * @throws IllegalArgumentException If the specified key is a
    *    JSONNull
    * @throws NullPointerException If either the key or value are
    *    java nulls.
    */
   JSONValue put(JSONString key, JSONValue value);

   /**
    * Get the named property's current value. This will return
    * java {@code null} if the property has not been defined on
    * this object. {@link JSONNull} will be returned if the
    * property is defined but its value is {@code null}
    *
    * @param key the property key to get
    * @return the associated JSONValue
    */
   JSONValue get(JSONString key);

   /**
    * Returns {@code true} if the specified property has been
    * defined on this object, {@code false} otherwise.
    *
    * @param key the property key to check
    * @return true if present, false otherwise
    */
   boolean containsKey(JSONString key);

   /**
    * Returns an unmodifiable set of the property names defined on
    * this object.
    *
    * @return set of property keys
    */
   Set<JSONString> keySet();

   /**
    * Returns an unmodifiable set of property entries and their
    * associated values.
    *
    * @return set of properties and their values
    */
   Set<Map.Entry<JSONString, JSONValue>> entrySet();

   /**
    * Returns an unmodifiable collection of property values currently
    * associated with this object.
    *
    * @return the object's values
    */
   Collection<JSONValue> values();

   /**
    * Returns {@code true} if this object has no defined properties,
    * {@code false} otherwise.
    *
    * @return boolean
    */
   boolean isEmpty();

   /**
    * Returns the number of properties currently defined on this object.
    *
    * @return size
    */
   int size();

}
