/**
 * Created by IntelliJ IDEA.
 * User: cfloersch
 * Date: 4/16/11 10:34 AM
 * Copyright XpertSoftware. All rights reserved.
 */
package xpertss.json;

import org.xpertss.json.desc.DescriptorFactory;
import org.xpertss.json.desc.EntityDescriptor;

/**
 * The entry point for marshalling and unmarshalling.
 * <p/>
 * This class provides a static creator method to create JSONMarshaller
 * instances capable of marshalling and unmarshalling specific entity
 * implementations.
 * <pre>
 *    JSONMarshaller<MyEntity> marshaller = JSONMarshaller.create(MyEntity.class);
 *    JSONObject encoded = marshaller.marshall(new MyEntity());
 *    String jsonString = JSON.stringify(encoded);
 *    ... send encoded ...
 * </pre>
 * <p/>
 * To decode a Json message received from the network:
 * <pre>
 *    JSONMarshaller<MyEntity> marshaller = JSONMarshaller.create(MyEntity.class);
 *    JSONObject object = (JSONObject) JSON.parse(jsonStringReadFromNetwork);
 *    MyEntity entity = marshaller.unmarshall(object);
 * </pre>
 * <p/>
 * An instance of the JSONMarshaller can be reused to encode and decode many
 * times.
 */
public class JSONMarshaller<T> {


   /**
    * Create an entity marshaller that can be used to marshall entity
    * instances into JSON objects or unmarshall JSON objects back into
    * entity instances.
    * <p/>
    * The returned instance may be used many times in both encode and
    * decode capacities.
    *
    * @param entity An entity class
    * @param <T> The entity class type.
    * @return A JSONMarshaller capable of marshalling and unmarshalling
    *    the specified entity type.
    * @throws IllegalArgumentException if an error occurs creating a
    *    marshaller for the given entity
    */
   public static <T> JSONMarshaller<T> create(Class<T> entity)
   {
      return new JSONMarshaller<>(DescriptorFactory.create(entity));
   }







   private final EntityDescriptor<T> descriptor;

   private JSONMarshaller(EntityDescriptor<T> descriptor)
   {
      this.descriptor = descriptor;
   }




   /**
    * Marshalls an entity {@code T} to its JSON object representation.
    *
    * @param entity an entity to marshall
    * @return a JSON object
    * @throws MarshallingException - If an error occurs while marshalling
    * @throws ArithmeticException - If a field within the entity cannot be
    *    narrowed to a 64-bit floating point representation without loss of
    *    precision.
    */
   public JSONObject marshall(T entity)
   {
      return marshall(entity, null);
   }


   /**
    * Marshalls a particular view of an entity {@code T} to its JSON object
    * representation.
    *
    * @param entity an entity to marshall
    * @param view   a view name (see {@link Value#views}) or {@code null}
    * @return a JSON object
    * @throws MarshallingException - If an error occurs while marshalling
    * @throws ArithmeticException - If a field within the entity cannot be
    *    narrowed to a 64-bit floating point representation without loss of
    *    precision.
    */
   public JSONObject marshall(T entity, String view)
   {
      return descriptor.marshall(entity, view);
   }





   /**
    * Unmarshalls the JSON object representation of an entity {@code T}.
    *
    * @param object a JSON object
    * @return the unmarshalled entity
    * @throws MarshallingException - If an error occurs while unmarshalling
    * @throws ArithmeticException - If a json number cannot be narrowed into
    *    the entity field's numeric scope without losing precision.
    */
   public T unmarshall(JSONObject object)
   {
      return unmarshall(object, null);
   }

   /**
    * Unmarshalls the JSON object representation of a particular view of an
    * entity {@code T}.
    *
    * @param object a JSON object
    * @param view  a view name (see {@link xpertss.json.Value#views}) or
    *    {@code null}
    * @return the unmarshalled entity
    * @throws MarshallingException - If an error occurs while unmarshalling
    * @throws ArithmeticException - If a json number cannot be narrowed into
    *    the entity field's numeric scope without losing precision.
    */
   public T unmarshall(JSONObject object, String view)
   {
      return descriptor.unmarshall(object, view);
   }


}
