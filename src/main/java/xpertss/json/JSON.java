package xpertss.json;


import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.Map.Entry;


import static org.xpertss.json.util.FloatType.*;
import static org.xpertss.json.util.IOUtils.skip;
import static java.util.Objects.*;

/**
 * JSON Utility class.
 * <p>
 * This class provides utility methods to transform JSON Values into
 * strings and from strings back into JSON Values.
 * <pre>
 *    String jsonFromNetwork = network.readString();
 *    JSONValue value = JSON.parse(jsonFromNetwork);
 *
 *    or
 *
 *    Reader reader = new InputStreamReader(socket.getInputStream());
 *    JSONValue value = JSON.parse(reader);
 * </pre>
 * <p>
 * This class also provides several methods to create JSON Objects,
 * Arrays, Numbers, and Strings from standard Java types.
 * <pre>
 *    JSONNumber ten = JSON.number(BigInteger.TEN);
 *    or
 *    JSONNumber ten = JSON.number(10);
 *
 *    JSONString hello = JSON.string("hello");
 *
 *    JSONObject object = JSON.object(JSON.string("value"), ten,
 *                         JSON.string("type"),JSON.string("number"));
 *
 *    JSONArray array = JSON.array(JSON.number(BigDecimal.ONE),
 *                                  JSON.number(2000L),JSON.number(2.5D));
 * </pre>
 * <p>
 * An alternative method to build JSON values is to use the builders.
 * <pre>
 *    JSONObject object = JSON.objectBuilder().add("value", BigInteger.TEN)
 *                         .add("type", "number").build();
 *
 *    JSONArray array = JSON.arrayBuilder().add(BigDecimal.ONE)
 *                            .add(2000L).add(2.5D).build();
 * </pre>
 * <p>
 * Builders may be layered together to make complex object graphs
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
 *
 *    JSONArray colors = JSON.arrayBuilder()
 *                         .add(JSON.objectBuilder()
 *                            .add("color","black")
 *                            .add("hex","000000"))
 *                         .add(JSON.objectBuilder()
 *                            .add("color","white")
 *                            .add("hex","ffffff"))
 *                         .build();
 *
 * </pre>
 */
public final class JSON {

   private JSON() { }

   private static abstract class BaseValue implements JSONValue {

      @Override
      public final String toString()
      {
         StringWriter writer = new StringWriter();
         visit(new PrettyPrinter("  ", writer));
         return writer.toString();
      }

   }

   private static class ObjectImpl extends BaseValue implements JSONObject {

      private final Map<JSONString, JSONValue> delegate = new LinkedHashMap<JSONString, JSONValue>();

      public void write(Writer writer)
         throws IOException
      {
         writer.append('{');
         String separator = "";
         for(JSONString key : delegate.keySet()) {
            writer.append(separator);
            separator = ",";
            key.write(writer);
            writer.append(':');
            delegate.get(key).write(writer);
         }
         writer.append('}');
      }

      public <T> T visit(JSONVisitor<T> visitor)
      {
         return visitor.caseObject(this);
      }


      @Override
      public boolean equals(Object o)
      {
         return o instanceof JSONObject && !NULL.equals(o) && delegate.entrySet().equals(((JSONObject) o).entrySet());
      }

      public JSONValue get(JSONString key)
      {
         return delegate.get(key);
      }

      public boolean containsKey(JSONString key)
      {
         return delegate.containsKey(key);
      }

      @Override
      public int hashCode()
      {
         return delegate.hashCode();
      }

      public boolean isEmpty()
      {
         return delegate.isEmpty();
      }

      public Set<JSONString> keySet()
      {
         return Collections.unmodifiableSet(delegate.keySet());
      }

      public Set<Entry<JSONString, JSONValue>> entrySet()
      {
         return Collections.unmodifiableSet(delegate.entrySet());
      }

      public JSONValue put(JSONString key, JSONValue value)
      {
         if(key == null)
            throw new NullPointerException("key may not be java null");
         if(value == null)
            throw new NullPointerException("value may not be java null");
         if(NULL.equals(key))
            throw new IllegalArgumentException("objects don't support null property names");
         return delegate.put(key, value);
      }

      public int size()
      {
         return delegate.size();
      }

      public Collection<JSONValue> values()
      {
         return Collections.unmodifiableCollection(delegate.values());
      }


   }

   private static class ArrayImpl extends BaseValue implements JSONArray {

      private final List<JSONValue> delegate = new LinkedList<JSONValue>();

      public ArrayImpl()
      {
      }

      public ArrayImpl(JSONValue... values)
      {
         for (JSONValue value : values) {
            delegate.add(requireNonNull(value, "java null not supported"));
         }
      }

      public void write(Writer writer)
         throws IOException
      {
         writer.append('[');
         java.lang.String separator = "";
         for(JSONValue value : delegate) {
            writer.append(separator);
            separator = ",";
            value.write(writer);
         }
         writer.append(']');
      }

      public <T> T visit(JSONVisitor<T> visitor)
      {
         return visitor.caseArray(this);
      }

      public List<JSONValue> values()
      {
         return Collections.unmodifiableList(delegate);
      }

      public void add(int index, JSONValue element)
      {
         delegate.add(index, requireNonNull(element, "java null not supported"));
      }

      public boolean add(JSONValue e)
      {
         return delegate.add(requireNonNull(e, "java null not supported"));
      }

      @Override
      public boolean equals(Object o)
      {
         if(!(o instanceof JSONArray)) {
            return false;
         } else if(NULL.equals(o)) {
            return false;
         } else {
            return delegate.equals(((JSONArray) o).values());
         }
      }

      public JSONValue get(int index)
      {
         return delegate.get(index);
      }

      @Override
      public int hashCode()
      {
         return delegate.hashCode();
      }

      public boolean isEmpty()
      {
         return delegate.isEmpty();
      }

      public int size()
      {
         return delegate.size();
      }

      public Iterator<JSONValue> iterator()
      {
         return values().iterator();
      }

   }

   private static class BooleanImpl extends BaseValue implements JSONBoolean {

      private final boolean b;

      public BooleanImpl(boolean b)
      {
         this.b = b;
      }

      public void write(Writer writer)
         throws IOException
      {
         writer.append(Boolean.toString(b));
      }

      public <T> T visit(JSONVisitor<T> visitor)
      {
         return visitor.caseBoolean(this);
      }

      @Override
      public boolean equals(Object obj)
      {
         if(obj == this) return true;
         if(obj instanceof JSONBoolean) {
            if(NULL.equals(obj)) return false;
            JSONBoolean that = (JSONBoolean) obj;
            return !(this.b ^ that.getBoolean());
         }
         return false;
      }

      @Override
      public int hashCode()
      {
         if(b) {
            return 982451653;
         } else {
            return 941083987;
         }
      }

      public boolean getBoolean()
      {
         return b;
      }

   }

   private static class NumberImpl extends BaseValue implements JSONNumber {

      private final BigDecimal number;

      public NumberImpl(BigDecimal number)
      {
         this.number = number;
      }

      public void write(Writer writer)
         throws IOException
      {
         writer.append(number.toPlainString());
      }


      public BigDecimal getNumber()
      {
         return number;
      }

      public <T> T visit(JSONVisitor<T> visitor)
      {
         return visitor.caseNumber(this);
      }

      @Override
      public boolean equals(Object obj)
      {
         if(obj == this) return true;
         if(obj instanceof JSONNumber) {
            if(NULL.equals(obj)) return false;
            JSONNumber o = (JSONNumber) obj;
            return getNumber().compareTo(o.getNumber()) == 0;
         }
         return false;
      }

      @Override
      public int hashCode()
      {
         return number.hashCode();
      }

   }

   private static class StringImpl extends BaseValue implements JSONString {

      private final String string;

      public StringImpl(String string)
      {
         this.string = string;
      }

      public void write(Writer writer)
         throws IOException
      {
         char[] data = string.toCharArray();
         int length = data.length;
         StringBuilder builder = new StringBuilder(length << 1);
         builder.append('"');
         int pos = 0;
         char c;
         for(int i = 0; i < length; i++) {
            switch(c = data[i]) {
               case '\b':
                  builder.append(data, pos, i - pos);
                  builder.append("\\b");
                  pos = i + 1;
                  break;
               case '\n':
                  builder.append(data, pos, i - pos);
                  builder.append("\\n");
                  pos = i + 1;
                  break;
               case '\f':
                  builder.append(data, pos, i - pos);
                  builder.append("\\f");
                  pos = i + 1;
                  break;
               case '\r':
                  builder.append(data, pos, i - pos);
                  builder.append("\\r");
                  pos = i + 1;
                  break;
               case '\t':
                  builder.append(data, pos, i - pos);
                  builder.append("\\t");
                  pos = i + 1;
                  break;
               case '"':
                  builder.append(data, pos, i - pos);
                  builder.append("\\\"");
                  pos = i + 1;
                  break;
               case '\\':
                  builder.append(data, pos, i - pos);
                  builder.append("\\\\");
                  pos = i + 1;
                  break;
               default:
                  if(c < 0x20 || (c >= 0x7f && c < 0xa0)) {
                     builder.append(data, pos, i - pos);
                     builder.append(String.format("\\u%04x", (int) c));
                     pos = i + 1;
                  }
                  break;
            }
         }
         builder.append(data, pos, length - pos).append('"');
         writer.append(builder.toString());
      }

      public <T> T visit(JSONVisitor<T> visitor)
      {
         return visitor.caseString(this);
      }

      public int compareTo(JSONString that)
      {
         return this.string.compareTo(that.getString());
      }

      @Override
      public boolean equals(Object obj)
      {
         if(obj == this) return true;
         if(obj instanceof JSONString && !NULL.equals(obj)) {
            JSONString that = (JSONString) obj;
            return Objects.equals(string, that.getString());
         }
         return false;
      }

      @Override
      public int hashCode()
      {
         return string.hashCode();
      }

      public String getString()
      {
         return string;
      }

      public boolean isEmpty()
      {
         return string.length() == 0; // 1.5 compatible
      }

   }

   private static class NullImpl extends BaseValue implements JSONNull {

      public void write(Writer writer)
         throws IOException
      {
         writer.append("null");
      }

      public <T> T visit(JSONVisitor<T> visitor)
      {
         return visitor.caseNull();
      }

      @Override
      public boolean equals(Object obj)
      {
         return obj instanceof JSON.NullImpl;
      }

      @Override
      public int hashCode()
      {
         return 900772187;
      }

      public boolean getBoolean()
      {
         throw new NullPointerException();
      }

      public BigDecimal getNumber()
      {
         throw new NullPointerException();
      }

      public String getString()
      {
         throw new NullPointerException();
      }

      public int compareTo(JSONString o)
      {
         throw new NullPointerException();
      }

      public JSONValue get(JSONString key)
      {
         throw new NullPointerException();
      }

      public boolean containsKey(JSONString key)
      {
         throw new NullPointerException();
      }

      public boolean isEmpty()
      {
         throw new NullPointerException();
      }

      public Set<JSONString> keySet()
      {
         throw new NullPointerException();
      }

      public Set<Entry<JSONString, JSONValue>> entrySet()
      {
         throw new NullPointerException();
      }

      public JSONValue put(JSONString key, JSONValue value)
      {
         throw new NullPointerException();
      }

      public int size()
      {
         throw new NullPointerException();
      }

      public List<JSONValue> values()
      {
         throw new NullPointerException();
      }

      public void add(int index, JSONValue element)
      {
         throw new NullPointerException();
      }

      public boolean add(JSONValue element)
      {
         throw new NullPointerException();
      }

      public JSONValue get(int index)
      {
         throw new NullPointerException();
      }

      public Iterator<JSONValue> iterator()
      {
         throw new NullPointerException();
      }

   }


   /**
    * A reusable constant representing a JSONNull..
    */
   public static final JSONNull NULL = new JSON.NullImpl();

   /**
    * A reusable JSONBoolean object corresponding to the primitive value
    * {@code true}.
    */
   public static final JSONBoolean TRUE = new JSON.BooleanImpl(true);

   /**
    * A reusable JSONBoolean object corresponding to the primitive value
    * {@code false}.
    */
   public static final JSONBoolean FALSE = new JSON.BooleanImpl(false);





   // TODO Do I want to use a scanner rather than a pushback reader??

   /**
    * Parse a JSON value from a given string.
    *
    * @param input the JSON input as a string
    * @return A JSONValue representation of the JSON String
    * @throws SyntaxException if an error is encountered in the
    *    JSON syntax
    */
   public static JSONValue parse(String input)
      throws SyntaxException
   {
      try {
         return parse(new StringReader(input));
      } catch(IOException e) {
         throw new Error(e);
      }
   }

   /**
    * Read and parse a JSON value from a given reader.
    *
    * @param reader the reader to read JSON from
    * @return A JSONValue representation of the JSON data
    * @throws IOException if an error occurs reading from the reader
    * @throws SyntaxException if an error is encountered in the
    *    JSON syntax
    */
   public static JSONValue parse(Reader reader)
      throws IOException, SyntaxException
   {
      PushbackReader stream = new PushbackReader(reader);
      int c = skip(stream);
      return (c != -1) ? read(stream, c) : null;
   }




   /**
    * Covert a JSONValue into a compact string to be sent across the network.
    * This differs from the toString method in that the output has spaces, tabs,
    * and newlines removed to compact the overall size of the string. This can in
    * many cases make the object more difficult to read.
    *
    * @param value the JSON value to stringify
    * @return a string representation of the JSONValue
    */
   public static String stringify(JSONValue value)
   {
      StringWriter writer = new StringWriter();
      try {
         value.write(writer);
      } catch(IOException e) {
         throw new Error(e);
      }
      return writer.toString();
   }


   /**
    * Create and return a new JSONObjectBuilder that can be used to
    * build JSONObject objects.
    *
    * @return a new object builder
    */
   public static JSONObjectBuilder objectBuilder()
   {
      return new JSONObjectBuilder();
   }

   /**
    * Create and return a new JSONArrayBuilder that can be used to
    * build JSONArray objects.
    *
    * @return a new array builder
    */
   public static JSONArrayBuilder arrayBuilder()
   {
      return new JSONArrayBuilder();
   }





   private static JSONValue read(PushbackReader reader, int c)
      throws IOException
   {
      switch(c) {
         // null
         case 'n': {
            int u = reader.read(), l1 = reader.read(), l2 = reader.read();
            if(u == 'u' && l1 == 'l' && l2 == 'l') {
               return JSON.NULL;
            } else {
               throw new SyntaxException("null expected");
            }
         }

         // true
         case 't': {
            int r = reader.read(), u = reader.read(), e = reader.read();
            if(r == 'r' && u == 'u' && e == 'e') {
               return JSON.TRUE;
            } else {
               throw new SyntaxException("true expected");
            }
         }

         // false
         case 'f': {
            int a = reader.read(), l = reader.read(), s = reader.read(), e = reader.read();
            if(a == 'a' && l == 'l' && s == 's' && e == 'e') {
               return JSON.FALSE;
            } else {
               throw new SyntaxException("false expected");
            }
         }

         // object
         case '{':
            return readObject(reader, c);

         // array
         case '[':
            return readArray(reader, c);

         // string
         case '"':
            return readString(reader, c);

         // number
         case '-':
         case '0':
         case '1':
         case '2':
         case '3':
         case '4':
         case '5':
         case '6':
         case '7':
         case '8':
         case '9':
            return readNumber(reader, c);

         // error
         default:
            throw new SyntaxException("illegal character " + (char) c);
      }
   }






   static JSONArray readArray(PushbackReader reader, int c) throws IOException
   {
      JSON.ArrayImpl array = new JSON.ArrayImpl();
      // Unrolling to avoid state, note that the first time around we must not
      // skip when reading the value added to the array.
      c = skip(reader);
      if(c == ']') return array;
      array.add(read(reader, c));
      c = skip(reader);
      do {
         if(c == ']') return array;
         array.add(read(reader, skip(reader)));
      } while((c = skip(reader)) == ',' || c == ']');
      throw new SyntaxException("non terminated array literal");
   }

   static JSONObject readObject(PushbackReader reader, int c) throws IOException
   {
      JSON.ObjectImpl object = new JSON.ObjectImpl();
      // Unrolling to avoid state, note that the first time around we must not
      // skip when reading the key of the object.
      c = skip(reader);
      if(c == '}') return object;
      if(c != '"') throw new SyntaxException("string key expected");
      JSONString key = readString(reader, c);
      if(skip(reader) != ':') throw new SyntaxException(": expected");
      object.put(key, read(reader, skip(reader)));


      c = skip(reader);
      do {
         if(c == '}') return object;
         if(c != ',') throw new SyntaxException("comma expected");

         c = skip(reader);
         if(c != '"') throw new SyntaxException("string key expected");
         key = readString(reader, c);
         if(skip(reader) != ':') throw new SyntaxException(": expected");
         object.put(key, read(reader, skip(reader)));
      } while((c = skip(reader)) == ',' || c == '}');

      throw new SyntaxException(String.format(
            "Non terminated object literal. Last character read was %s. " +
                  "Partial object literal read %s.",
            Character.toString((char) c),
            object.toString()));
   }

   static JSONString readString(PushbackReader reader, int c) throws IOException
   {
      StringBuilder sb = new StringBuilder();
      do {
         switch(c = reader.read()) {
            case '"':
               return new JSON.StringImpl(sb.toString());
            case '\\':
               switch(c = reader.read()) {
                  case '"':
                  case '\\':
                  case '/':
                     sb.append((char) c);
                     break;
                  case 'b':
                     sb.append('\b');
                     break;
                  case 'f':
                     sb.append('\f');
                     break;
                  case 'n':
                     sb.append('\n');
                     break;
                  case 'r':
                     sb.append('\r');
                     break;
                  case 't':
                     sb.append('\t');
                     break;
                  case 'u':
                     int h1 = reader.read(), h2 = reader.read(), h3 = reader.read(), h4 = reader.read();
                     if(('a' <= h1 && h1 <= 'f' || 'A' <= h1 && h1 <= 'F' || '0' <= h1 && h1 <= '9') &&
                           ('a' <= h2 && h2 <= 'f' || 'A' <= h2 && h2 <= 'F' || '0' <= h2 && h2 <= '9') &&
                           ('a' <= h3 && h3 <= 'f' || 'A' <= h3 && h3 <= 'F' || '0' <= h3 && h3 <= '9') &&
                           ('a' <= h4 && h4 <= 'f' || 'A' <= h4 && h4 <= 'F' || '0' <= h4 && h4 <= '9')) {
                        sb.append((char) (fromHex(h1) << 12 | fromHex(h2) << 8 | fromHex(h3) << 4 | fromHex(h4)));
                     } else {
                        throw new SyntaxException("invalid hex code");
                     }
               }
               break;
            case -1:
               throw new SyntaxException("non terminated string");
            default:
               sb.append((char) c);
         }
      } while(true);
   }


   static JSONNumber readNumber(PushbackReader reader, int c) throws IOException
   {
      StringBuilder sb = new StringBuilder();
      do {
         sb.append((char) c);
      } while((c = reader.read()) <= '9' && '0' <= c);
      switch(c) {
         case '.':
            do {
               sb.append((char) c);
            } while((c = reader.read()) <= '9' && '0' <= c);
         case 'e':
         case 'E':
            if(c != 'e' && c != 'E') {
               reader.unread(c);
               return new NumberImpl(new BigDecimal(sb.toString()));
            }
            sb.append((char) c);
            c = reader.read();
            if(c != '+' && c != '-' && c < '0' && '9' < c) {
               reader.unread(c);
               return new NumberImpl(new BigDecimal(sb.toString()));
            }
            do {
               sb.append((char) c);
            } while((c = reader.read()) <= '9' && '0' <= c);
         default:
            reader.unread(c);
            return new NumberImpl(new BigDecimal(sb.toString()));
      }
   }


   /* Visible for testing. */
   static int fromHex(int codePoint)
   {
      // '9' = 57, 'F' = 70, 'f' = 102
      return (codePoint <= '9') ? codePoint - '0' :
         (codePoint <= 'F') ? codePoint - 'A' + 10 : codePoint - 'a' + 10;
   }


   /**
    * Create and return a new empty json array.
    *
    * @return a new array
    */
   public static JSONArray array()
   {
      return new JSON.ArrayImpl();
   }

   /**
    * Create and return a new json array pre-populated with the given
    * json values.
    *
    * @param values the values for the array
    * @return a new JSONArray object
    */
   public static JSONArray array(JSONValue ... values)
   {
      return new JSON.ArrayImpl(values);
   }


   /**
    * Create and return a new empty json object.
    *
    * @return a new JSON object
    */
   public static JSONObject object()
   {
      return new JSON.ObjectImpl();
   }

   /**
    * Create and return a new json object pre-populated with the given
    * named values.
    * <p>
    * The specified value set MUST contain an even number of entries and
    * all odd items MUST be instances of JSONString
    *
    * @param keyValuePairs the properties and their value
    * @return a JSONObject object
    */
   public static JSONObject object(JSONValue... keyValuePairs)
   {
      if(keyValuePairs.length % 2 != 0) {
         throw new IllegalArgumentException("Number of arguments must be even");
      }
      JSONObject o = object();
      for(int i = 0; i < keyValuePairs.length; i += 2) {
         JSONValue key = keyValuePairs[i];
         if(key instanceof JSONString) {
            o.put((JSONString) key, keyValuePairs[i + 1]);
         } else {
            throw new IllegalArgumentException("Keys must be JSON strings");
         }
      }
      return o;
   }


   private static final BigInteger MAX_INTEGER = BigInteger.valueOf(2).pow(53);
   private static final BigInteger MIN_INTEGER = BigInteger.valueOf(2).pow(53).negate();

   private static final long MAX_LONG = MAX_INTEGER.longValue();
   private static final long MIN_LONG = MIN_INTEGER.longValue();


   /**
    * Create and return a new JSONNumber with the given value.
    *
    * @param number the BigInteger number value
    * @return a JSONNumber object
    * @throws ArithmeticException If the given big integer can not be
    *    represented with a 64-bit floating point number without loss
    *    of precision.
    */
   public static JSONNumber number(BigInteger number)
   {
      requireNonNull(number);
      if(MAX_INTEGER.compareTo(number) < 0 || MIN_INTEGER.compareTo(number) > 0) {
         // We lose precision in decimal 64 when the integral exceeds the 52 bit mantissa
         new JSON.NumberImpl(Decimal64.checkCast(new BigDecimal(number)));
      }
      return new JSON.NumberImpl(new BigDecimal(number));
   }

   /**
    * Create and return a new JSONNumber with the given value.
    *
    * @param number the long number value
    * @return a JSONNumber object
    * @throws ArithmeticException If the given long can not be represented
    *    with a 64-bit floating point number without loss of precision.
    */
   public static JSONNumber number(long number)
   {
      if(number < MIN_LONG || number > MAX_LONG) {
         // We lose precision in decimal 64 when the integral exceeds the 52 bit mantissa
         new JSON.NumberImpl(Decimal64.checkCast(BigDecimal.valueOf(number)));
      }
      return new JSON.NumberImpl(BigDecimal.valueOf(number));
   }

   /**
    * Create and return a new JSONNumber with the given value.
    *
    * @param number the float number value
    * @return a JSONNumber object
    */
   public static JSONNumber number(float number)
   {
      return new JSON.NumberImpl(new BigDecimal(Float.toString(number)));
   }

   /**
    * Create and return a new JSONNumber with the given value.
    *
    * @param number the double number value
    * @return a JSONNumber object
    */
   public static JSONNumber number(double number)
   {
      return new JSON.NumberImpl(BigDecimal.valueOf(number));
   }


   /**
    * Create and return a new JSONNumber with the given value.
    *
    * @param  number the decimal number value
    * @return a JSONNumber object
    * @throws ArithmeticException If the given big decimal can not be
    *    represented with a 64-bit floating point number without loss
    *    of precision.
    */
   public static JSONNumber number(BigDecimal number)
   {
      return new JSON.NumberImpl(Decimal64.checkCast(requireNonNull(number)));
   }


   /**
    * Create and return a new JSONString with the given value.
    *
    * @param string the string value
    * @return a JSONString object
    */
   public static JSONString string(String string)
   {
      return new JSON.StringImpl(requireNonNull(string));
   }


}
