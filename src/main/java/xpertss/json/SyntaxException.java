/**
 * Created by IntelliJ IDEA.
 * User: cfloersch
 * Date: 4/11/11 7:52 AM
 * Copyright Manheim online
 */
package xpertss.json;

/**
 * Thrown by the JSON parser to indicate a syntax error in the
 * underlying json text.
 */
public class SyntaxException extends RuntimeException {

   public SyntaxException()
   {
   }

   public SyntaxException(String message)
   {
      super(message);
   }

   public SyntaxException(String message, Throwable cause)
   {
      super(message, cause);
   }

   public SyntaxException(Throwable cause)
   {
      super(cause);
   }
}
