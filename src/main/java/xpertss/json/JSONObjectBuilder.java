package xpertss.json;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import static xpertss.json.JSON.*;
import static xpertss.json.JSON.number;

/**
 * A builder for creating JSONObject models from scratch. This interface
 * initializes an empty JSON object model and provides methods to add
 * name/value pairs to the object model and to return the resulting
 * object. The methods in this class can be chained to add multiple
 * name/value pairs to the object.
 * <br>
 * <pre>
 *    JSONObject object = JSON.objectBuilder()
 *                         .add("firstName", "Chris")
 *                         .add("lastName", "Singer")
 *                         .add("email", JSON.objectBuilder()
 *                            .add("address","csinger@go.com")
 *                            .add("type", "work"))
 *                         .add("certs", JSON.arrayBuilder()
 *                            .add("j2ee").add("j2se").add("cisco"))
 *                         .add("age", 36)
 *                         .add("kids", JSON.NULL).build();
 * </pre>
 */
public class JSONObjectBuilder {

   private Map<String,Object> entries = new LinkedHashMap<>();

   /**
    * Add a string property to the built object.
    *
    * @param name the object property name
    * @param value the object property value
    * @return this object builder
    */
   public JSONObjectBuilder add(String name, String value)
   {
      entries.put(name, string(Objects.requireNonNull(value)));
      return this;
   }

   /**
    * Add a string property to the built object.
    *
    * @param name the object property name
    * @param value the object property value
    * @return this object builder
    */
   public JSONObjectBuilder add(String name, char value)
   {
      return add(name, Character.toString(value));
   }

   /**
    * Add a boolean property to the built object.
    *
    * @param name the object property name
    * @param value the object property value
    * @return this object builder
    */
   public JSONObjectBuilder add(String name, boolean value)
   {
      entries.put(name, (value) ? TRUE : FALSE);
      return this;
   }

   /**
    * Add a numeric property to the built object.
    *
    * @param name the object property name
    * @param value the object property value
    * @return this object builder
    */
   public JSONObjectBuilder add(String name, BigInteger value)
   {
      entries.put(name, number(Objects.requireNonNull(value)));
      return this;
   }

   /**
    * Add a numeric property to the built object.
    *
    * @param name the object property name
    * @param value the object property value
    * @return this object builder
    */
   public JSONObjectBuilder add(String name, BigDecimal value)
   {
      entries.put(name, number(Objects.requireNonNull(value)));
      return this;
   }

   /**
    * Add a numeric property to the built object.
    *
    * @param name the object property name
    * @param value the object property value
    * @return this object builder
    */
   public JSONObjectBuilder add(String name, byte value)
   {
      entries.put(name, number(value));
      return this;
   }




   /**
    * Add a numeric property to the built object.
    *
    * @param name the object property name
    * @param value the object property value
    * @return this object builder
    */
   public JSONObjectBuilder add(String name, short value)
   {
      entries.put(name, number(value));
      return this;
   }


   /**
    * Add a numeric property to the built object.
    *
    * @param name the object property name
    * @param value the object property value
    * @return this object builder
    */
   public JSONObjectBuilder add(String name, int value)
   {
      entries.put(name, number(value));
      return this;
   }


   /**
    * Add a numeric property to the built object.
    *
    * @param name the object property name
    * @param value the object property value
    * @return this object builder
    */
   public JSONObjectBuilder add(String name, long value)
   {
      entries.put(name, number(value));
      return this;
   }


   /**
    * Add a numeric property to the built object.
    *
    * @param name the object property name
    * @param value the object property value
    * @return this object builder
    */
   public JSONObjectBuilder add(String name, float value)
   {
      entries.put(name, number(value));
      return this;
   }


   /**
    * Add a numeric property to the built object.
    *
    * @param name the object property name
    * @param value the object property value
    * @return this object builder
    */
   public JSONObjectBuilder add(String name, double value)
   {
      entries.put(name, number(value));
      return this;
   }


   /**
    * Add a null property to the built object.
    *
    * @param name the object property name
    * @return this object builder
    */
   public JSONObjectBuilder addNull(String name)
   {
      entries.put(name, NULL);
      return this;
   }



   /**
    * Add an object property to the built object.
    *
    * @param name the object property name
    * @param builder the builder to generate the value
    * @return this object builder
    */
   public JSONObjectBuilder add(String name, JSONObjectBuilder builder)
   {
      entries.put(name, Objects.requireNonNull(builder));
      return this;
   }

   /**
    * Add an array property to the built object.
    *
    * @param name the object property name
    * @param builder the builder to generate the value
    * @return this object builder
    */
   public JSONObjectBuilder add(String name, JSONArrayBuilder builder)
   {
      entries.put(name, Objects.requireNonNull(builder));
      return this;
   }



   /**
    * Build the object composed of the properties previously added
    * to this builder.
    * <br>
    * Sub objects and arrays are built at this time as well.
    *
    * @return a JSONObject
    */
   public JSONObject build()
   {
      JSONObject object = object();
      for(Map.Entry<String,Object> entry : entries.entrySet()) {
         if(entry.getValue() instanceof JSONValue) {
            object.put(string(entry.getKey()), (JSONValue) entry.getValue());
         } else if(entry.getValue() instanceof JSONObjectBuilder) {
            JSONObjectBuilder builder = (JSONObjectBuilder) entry.getValue();
            object.put(string(entry.getKey()), builder.build());
         } else if(entry.getValue() instanceof JSONArrayBuilder) {
            JSONArrayBuilder builder = (JSONArrayBuilder) entry.getValue();
            object.put(string(entry.getKey()), builder.build());
         } else {
            throw new Error("Should never happen");
         }
      }
      return object;
   }







}
