/*
 * Created by IntelliJ IDEA.
 * User: cfloersch
 * Date: 4/8/11 9:48 AM
 * Copyright Manheim online
 */
package xpertss.json;

import java.io.IOException;
import java.io.Writer;

/**
 * The base class of all JSON Value types.
 */
public interface JSONValue {

   /**
    * Writes the value of this json value to the given writer.
    *
    * @param writer The writer to write json data to
    * @throws IOException if an error occurs while writing
    */
   void write(Writer writer)
      throws IOException;

   /**
    * Visit the specified visitor in a type specific way.
    *
    * @param visitor the visitor to visit
    * @param <T> the type of the visited object
    * @return the result of the visit
    */
   <T> T visit(JSONVisitor<T> visitor);

   /**
    * Returns a JSON representation of this value.
    * <br>
    * The returned string is formatted to be read by a human and
    * as such is less than optimum for network transmission.
    *
    * @return a JSON representation of this value
    */
   String toString();

}
