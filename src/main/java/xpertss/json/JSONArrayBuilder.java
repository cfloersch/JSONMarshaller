package xpertss.json;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static xpertss.json.JSON.*;


/**
 * A builder for creating JSONArray models from scratch. This interface
 * initializes an empty JSON array model and provides methods to add
 * values to the array model and to return the resulting array. The
 * methods in this class can be chained to add multiple values to the
 * array.
 * <br>
 * <pre>
 *    JSONArray colors = JSON.arrayBuilder()
 *                         .add(JSON.objectBuilder()
 *                            .add("color","black")
 *                            .add("hex","000000"))
 *                         .add(JSON.objectBuilder()
 *                            .add("color","white")
 *                            .add("hex","ffffff"))
 *                         .build();
 * </pre>
 *
 */
public class JSONArrayBuilder {

   private List items = new ArrayList();


   /**
    * Add a numeric element to the built array
    *
    * @param value the element value to add to the array
    * @return this array builder
    */
   public JSONArrayBuilder add(BigDecimal value)
   {
      items.add(number(Objects.requireNonNull(value)));
      return this;
   }

   /**
    * Add a numeric element to the built array
    *
    * @param value the element value to add to the array
    * @return this array builder
    */
   public JSONArrayBuilder add(BigInteger value)
   {
      items.add(number(Objects.requireNonNull(value)));
      return this;
   }

   /**
    * Add a numeric element to the built array
    *
    * @param value the element value to add to the array
    * @return this array builder
    */
   public JSONArrayBuilder add(byte value)
   {
      items.add(number(value));
      return this;
   }

   /**
    * Add a numeric element to the built array
    *
    * @param value the element value to add to the array
    * @return this array builder
    */
   public JSONArrayBuilder add(short value)
   {
      items.add(number(value));
      return this;
   }

   /**
    * Add a numeric element to the built array
    *
    * @param value the element value to add to the array
    * @return this array builder
    */
   public JSONArrayBuilder add(int value)
   {
      items.add(number(value));
      return this;
   }

   /**
    * Add a numeric element to the built array
    *
    * @param value the element value to add to the array
    * @return this array builder
    */
   public JSONArrayBuilder add(long value)
   {
      items.add(number(value));
      return this;
   }

   /**
    * Add a numeric element to the built array
    *
    * @param value the element value to add to the array
    * @return this array builder
    */
   public JSONArrayBuilder add(float value)
   {
      items.add(number(value));
      return this;
   }

   /**
    * Add a numeric element to the built array
    *
    * @param value the element value to add to the array
    * @return this array builder
    */
   public JSONArrayBuilder add(double value)
   {
      items.add(number(value));
      return this;
   }



   /**
    * Add a string element to the built array
    *
    * @param value the element value to add to the array
    * @return this array builder
    */
   public JSONArrayBuilder add(String value)
   {
      items.add(string(Objects.requireNonNull(value)));
      return this;
   }

   /**
    * Add a string element to the built array
    *
    * @param value the element value to add to the array
    * @return this array builder
    */
   public JSONArrayBuilder add(char value)
   {
      items.add(string(Character.toString(value)));
      return this;
   }

   /**
    * Add a boolean element to the built array
    *
    * @param value the element value to add to the array
    * @return this array builder
    */
   public JSONArrayBuilder add(boolean value)
   {
      items.add( (value) ? TRUE : FALSE);
      return this;
   }

   /**
    * Add a null element to the built array
    *
    * @return this array builder
    */
   public JSONArrayBuilder addNull()
   {
      items.add(NULL);
      return this;
   }


   /**
    * Add an object element to the built array
    *
    * @param builder the builder to pull an object from
    * @return this array builder
    */
   public JSONArrayBuilder add(JSONObjectBuilder builder)
   {
      items.add(Objects.requireNonNull(builder));
      return this;
   }

   /**
    * Add an array element to the built array
    *
    * @param builder array builder to pull elements from
    * @return this array builder
    */
   public JSONArrayBuilder add(JSONArrayBuilder builder)
   {
      items.add(Objects.requireNonNull(builder));
      return this;
   }



   /**
    * Build the array composed of the elements previously added
    * to this builder.
    * <p>
    * Sub objects and arrays are built at this time as well.
    *
    * @return a JSONArray
    */
   public JSONArray build()
   {
      JSONArray array = array();
      for(Object item : items) {
         if(item instanceof JSONValue) {
            array.add((JSONValue)item);
         } else if(item instanceof JSONObjectBuilder) {
            JSONObjectBuilder builder = (JSONObjectBuilder) item;
            array.add(builder.build());
         } else if(item instanceof JSONArrayBuilder) {
            JSONArrayBuilder builder = (JSONArrayBuilder) item;
            array.add(builder.build());
         } else {
            throw new Error("Should never happen");
         }
      }
      return array;
   }

}
