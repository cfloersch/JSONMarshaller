/**
 * Created by IntelliJ IDEA.
 * User: cfloersch
 * Date: 4/8/11 9:54 AM
 * Copyright Manheim online
 */
package xpertss.json;

/**
 * A binary property that can be either {@code true} or
 * {@code false}.
 *
 * @see JSON#TRUE
 * @see JSON#FALSE
 */
public interface JSONBoolean extends JSONValue {

   /**
    * Return this boolean's value as a java boolean
    *
    * @return the boolean's value
    */
   boolean getBoolean();

}
