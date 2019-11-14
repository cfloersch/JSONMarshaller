/*
 * Created by IntelliJ IDEA.
 * User: cfloersch
 * Date: 4/8/11 9:50 AM
 * Copyright Manheim online
 */
package xpertss.json;

/**
 * A JSON value that represents a string.
 */
public interface JSONString extends JSONValue, Comparable<JSONString> {

   /**
    * Get the string value associated with this json value.
    *
    * @return the string value
    */
   String getString();

   /**
    * Returns {@code true} if the string is zero length.
    *
    * @return true if the string is zero length, false otherwise
    */
   boolean isEmpty();

}
