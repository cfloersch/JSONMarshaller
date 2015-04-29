package org.xpertss.json.desc;

import xpertss.json.JSONValue;

/**
 * Descriptor used as a synthesis of an object. An object can be an entity,
 * a collection, a map, an array or a user defined type.
 *
 * @param <E> the type of the entity being described
 * @param <J> the type of the marshalled entity
 */
public interface Descriptor<E, J extends JSONValue> {

   /**
    * Returns the {@link Class} of the object described.
    */
   Class<?> getReturnedClass();




   /**
    * Marshall the described object.
    * <p/>
    * There are two main uses for this method. When it comes to system objects
    * like BigDecimal, String, etc this method is called by either the array or
    * field marshaller methods to perform the actual marshalling while they
    * handle the field and array index referencing.
    * <p/>
    * If this implementation represents an entity this method will be called by
    * the top level marshaller to begin the marshalling process.
    *
    * @param entity an instance of the described object
    * @return the marshalled object
    */
   J marshall(E entity, String view);

   /**
    * Unmarshall the given marshalled value.
    * <p/>
    * If the implementation of this interface is a value descriptor like a string
    * or BigDecimal than this method is called by either the array or field
    * unmarshaller methods to perform the actual unmarshalling while they
    * handle the field and array index referencing.
    * <p/>
    * If this implementation represents an entity this method will be called by
    * the top level marshaller to begin the unmarshalling process.
    *
    * @param marshalled the marshalled object
    */
   E unmarshall(J marshalled, String view);





   /**
    * Marshall a field from the specified entity and return it. The supplied field
    * descriptor defines the field name of the item in the entity to retrieve and
    * provides basic accessor methods to obtain those values in a descriptor
    * dependent way. For example one descriptor may access the field data as an
    * object while another as a primitive.
    * <p/>
    * This will always be called by an entity
    */
   JSONValue marshall(FieldDescriptor fieldDescriptor, Object entity, String view);

   /**
    * Unmarshall the specified value and set it on the given entity using the field
    * descriptor's accessor methods and it's defined field name. This is called by
    * an entity to decode one of its fields which may be a string, number, array, or
    * another entity.
    * <p/>
    * This will always be called by an entity
    */
   void unmarshall(FieldDescriptor fieldDescriptor, Object entity, JSONValue marshalled, String view);





   /**
    * Since an array may contain objects or primitives it is the descriptors job to
    * access the elements and transform them into a JSON value object which should
    * be returned.
    * <p/>
    * This will only be called by an Array descriptor
    */
   J marshallArray(Object array, int index, String view);

   /**
    * Since an array may contain objects or primitives it iss the descriptors job to
    * convert the JSON value into the appropriate type and set it onto the given
    * array using a type specific method..
    * <p/>
    * This will only be called by an Array descriptor
    */
   void unmarshallArray(Object array, JSONValue value, int index, String view);





   String toString(int pad);

}
