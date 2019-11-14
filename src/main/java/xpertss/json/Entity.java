package xpertss.json;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Describes a JSON entity. This annotation should be applied to any
 * java object that will be marshalled or unmarshalled using this
 * library. That should include not only the top level object but
 * all objects referenced by value fields within.
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = { ElementType.TYPE })
public @interface Entity {


   /**
    * List of subclasses of an entity. All the subclasses will be marshallable.
    * This option must be used in conjunction with {@link #discriminatorName()}.
    *
    * @return the entity's subclasses
    */
   Class<?>[] subclasses() default { };

   /**
    * The discriminator name. This option must be used in conjunction with
    * {@link #subclasses()}.
    *
    * @return the discriminator name
    */
   String discriminatorName() default "";

   /**
    * The value of the discriminator for this entity. This value is used if the
    * entity is mentioned in a parent's {@link #subclasses()} option.
    *
    * @return the discriminator value
    */
   String discriminator() default "";

}
