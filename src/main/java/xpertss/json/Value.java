package xpertss.json;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A JSON value.
 * <p/>
 * Object fields that should be encoded to and decoded from json
 * should be annotated with this annotation.
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = { ElementType.FIELD, ElementType.METHOD })
public @interface Value {

   /**
    * The JSON name. By default, the field name is chosen.
    */
   String name() default "";

   /**
    * Whether the value is optional or not.
    * <p/>
    * If the field is not present in the JSON object during umarshalling
    * a value identified as optional will not be set and thus retain its
    * default value for the particular type. Otherwise, an exception will
    * be thrown indicating that it is missing from the json input.
    * <p/>
    * During marshalling optional only applies to objects in which case
    * {@code null} field values will not be encoded into the json object.
    * Primitives will always be encoded.
    */
   boolean optional() default false;

   /**
    * Views in which this value should be included.
    */
   String[] views() default { };

}
