package org.xpertss.json.desc;

import xpertss.json.*;

import java.lang.reflect.Array;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static java.lang.String.format;
import static xpertss.json.JSON.*;

/**
 * Created by cfloersch on 6/9/2014.
 */
public class PolymorphicEntityDescriptor<E> extends AbstractDescriptor<E, JSONObject> implements EntityDescriptor<E> {


   /**
    * Subclasses' descriptors used if the described entity is a polymorphic
    * entity. The keys are discriminators values.
    */
   private final Map<JSONString, EntityDescriptor<?>> subDescriptorsByDisciminator;

   /**
    * Subclasses' descriptors used if the described entity is a polymorphic
    * entity. The keys are discriminators values.
    */
   private final Map<Class<?>, EntityDescriptor<?>> subDescriptorsByClass;

   /**
    * The returned class of this entity. This class is the superclass of all
    * the entities produced by this polymorphic descriptor.
    */
   private final Class<?> returnedClass;

   /**
    * The name of the discriminator property.
    */
   private final String discriminatorName;


   public PolymorphicEntityDescriptor(Class<? extends E> returnedClass,
                                      String discriminatorName,
                                      Set<EntityDescriptor<?>> descriptors)
   {
      super(returnedClass);
      this.returnedClass = returnedClass;
      this.discriminatorName = discriminatorName;

      this.subDescriptorsByClass = new LinkedHashMap<Class<?>, EntityDescriptor<?>>();
      this.subDescriptorsByDisciminator = new LinkedHashMap<JSONString, EntityDescriptor<?>>();
      for (EntityDescriptor<?> descriptor : descriptors) {
         subDescriptorsByClass.put(descriptor.getReturnedClass(), descriptor);
         subDescriptorsByDisciminator.put(string(descriptor.getDiscriminator()), descriptor);
      }

   }


   @Override
   public String getDiscriminator()
   {
      return null;
   }

   @Override
   public Set<FieldDescriptor> getFieldDescriptors()
   {
      return null;
   }


   @Override
   public Class<?> getReturnedClass()
   {
      return returnedClass;
   }


   @Override
   public JSONObject marshall(E entity, String view)
   {
      if (entity == null) return NULL;

      EntityDescriptor<Object> descriptor =
            (EntityDescriptor<Object>) subDescriptorsByClass.get(entity.getClass());
      if (descriptor == null) {
         throw new MarshallingException(
               "Unmarshalled entity of class " + entity.getClass() + "is not " +
                     "a valid subclass entity of " + returnedClass);
      }
      JSONObject jsonObject = descriptor.marshall(entity, view);
      jsonObject.put(string(discriminatorName),
            string(descriptor.getDiscriminator()));
      return jsonObject;
   }

   @Override
   public E unmarshall(JSONObject marshalled, String view)
   {
      if (NULL.equals(marshalled)) return null;

      if (!marshalled.containsKey(string(discriminatorName))) {
         throw new MarshallingException(
               "Unmarhsalling polymorphic entity which does not contain the " +
                     "discriminator: " + discriminatorName);
      }
      EntityDescriptor<?> descriptor =
            subDescriptorsByDisciminator.get(marshalled.get(string(discriminatorName)));
      return (E) descriptor.unmarshall(marshalled, view);
   }


   @Override
   public String toString()
   {
      return toString(0);
   }

   @Override
   public String toString(int pad)
   {
      StringBuilder builder = new StringBuilder();
      builder.append("PolymorphicEntityDescriptor<" + getReturnedClass().getSimpleName() + ">\n");
      return builder.toString();
   }

}
