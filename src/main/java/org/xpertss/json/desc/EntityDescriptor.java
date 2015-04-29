package org.xpertss.json.desc;

import xpertss.json.JSONObject;

import java.util.Set;

/**
 * Entity descriptor describing entities which are instances of {@code T}.
 */
public interface EntityDescriptor<T> extends Descriptor<T, JSONObject> {

   /**
    * Gets the entity's discriminator. The discriminator is used only in
    * polymorphic situations to identify the subclasses.
    */
   String getDiscriminator();


   /**
    * Gets the set of field descriptors describing fields of this entity.
    */
   Set<FieldDescriptor> getFieldDescriptors();

}
