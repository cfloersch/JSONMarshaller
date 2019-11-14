package xpertss.json.spi;

import xpertss.json.JSONValue;

/**
 * JSON type to extend basic marshalling. These are user defined types.
 * <p>
 * A sample user type which encodes and decodes JSONString objects into
 * URI java types.
 * <pre>
 *    public class URIType implements JSONUserType&lt;URI, JSONString&gt; {
 *       public JSONString marshall(URI entity) {
 *          return string(entity.toString());
 *       }
 *
 *       public URI unmarshall(JSONString object) {
 *          try {
 *             return new URI(object.getString());
 *          } catch(URISyntaxException e) {
 *             throw new MarshallingException("invalid uri syntax", e);
 *          }
 *       }
 *
 *       public Class&lt;URI&gt; getReturnedClass() {
 *          return URI.class;
 *       }
 *    }
 * </pre>
 */
public interface JSONUserType<T, J extends JSONValue> {

   /**
    * Handles the marshalling of an object.
    *
    * @param object the object to marshall (never <tt>null</tt>)
    * @return the marshalled object
    */
   public J marshall(T object);

   /**
    * Handles the unmarshalling of an object.
    *
    * @param object the object to unmarshall (never <tt>null</tt>)
    * @return the unmarshalled object
    */
   public T unmarshall(J object);

   /**
    * Returns the java type the user type operates on.
    *
    * @return the class type of unmarshalled objects
    */
   public Class<T> getReturnedClass();

}
