/*
 * Created By: cfloersch
 * Date: 6/6/2014
 * Copyright 2013 XpertSoftware
 */
package xpertss.json.spi;

import xpertss.json.JSONValue;

import java.lang.reflect.Type;

/**
 * A service provider interface for all user defined service providers that
 * provide UserType support.
 * <br>
 * To make a service provider implementation available to the java runtime
 * a file /META-INF/services/xpertss.json.spi.UserTypeService must be packaged
 * along with the implementation into a jar together. The file should contain
 * the fully qualified class name of the user type service implementation.
 * <br>
 * An example implementation
 * <pre>
 *    public class MyUserTypes implements UserTypeService {
 *       public JSONUserType&lt;?, ? extends JSONValue&gt; create(Type type) {
 *          if(type instanceof Class) {
 *             Class klass = (Class) type;
 *             if(klass == URI.class) {
 *                return new URIType();
 *             }
 *          }
 *          return null;
 *       }
 *    }
 * </pre>
 * <br>
 * For user types which need to understand generic information associated with
 * the field you should operate on the Type parameter rather than the class.
 *
 * @see JSONUserType
 */
public interface UserTypeService {

   /**
    * A service provider must implement this method and return a {@link JSONUserType}
    * implementation for the given type if it can. If it can not provide an
    * implementation it should return {@code null}.
    *
    * @param type The Type associated with the class (possibly generic information)
    * @return a JSONUserType for the given type
    */
   public JSONUserType<?, ? extends JSONValue> create(Type type);

}
