package org.xpertss.json.desc;

import xpertss.json.JSONObject;

import java.util.Set;

/**
 * Entity descriptor describing entities which are instances of {@code T}.
 *
 * @param <T> the type of the entity
 */
public interface EntityDescriptor<T> extends Descriptor<T, JSONObject> {

   /**
    * Gets the entity's discriminator. The discriminator is used only in
    * polymorphic situations to identify the subclasses.
    *
    * @return the discriminator
    */
   String getDiscriminator();


   /**
    * Gets the set of field descriptors describing fields of this entity.
    *
    * @return the set of field descriptors for the entity
    */
   Set<FieldDescriptor> getFieldDescriptors();

}
