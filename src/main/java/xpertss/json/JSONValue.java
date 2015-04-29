/**
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
    */
   void write(Writer writer)
      throws IOException;

   /**
    * Visit the specified visitor in a type specific way.
    */
   <T> T visit(JSONVisitor<T> visitor);

   /**
    * Returns a JSON representation of this value.
    * <p/>
    * The returned string is formatted to be read by a human and
    * as such is less than optimum for network transmission.
    *
    * @return a JSON representation of this value
    */
   String toString();

}
