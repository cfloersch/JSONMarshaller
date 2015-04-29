/**
 * Created by IntelliJ IDEA.
 * User: cfloersch
 * Date: 4/8/11 9:52 AM
 * Copyright Manheim online
 */
package xpertss.json;

import java.util.List;

/**
 * An array, i.e. {@code ["hello","world"]}.
 */
public interface JSONArray extends JSONValue, Iterable<JSONValue> {

   /**
    * Add an element at the specified index.
    *
    * @throws NullPointerException If the element is {@code null}
    * @throws IndexOutOfBoundsException if the index is out of range
    *         (<tt>index &lt; 0 || index &gt; size()</tt>)
    */
   void add(int index, JSONValue element);

   /**
    * Add the given element to the end of the array.
    *
    * @throws NullPointerException If the element is {@code null}
    */
   boolean add(JSONValue element);

   /**
    * Get the element at the specified index.
    *
    * @throws IndexOutOfBoundsException if the index is out of range
    *         (<tt>index &lt; 0 || index &gt; size()</tt>)
    */
   JSONValue get(int index);

   /**
    * Returns {@code true} if this array currently has no elements,
    * {@code false} otherwise.
    */
   boolean isEmpty();

   /**
    * Returns the number of elements currently allocated to this array.
    */
   int size();

   /**
    * Return an unmodifiable list of the values currently contained in
    * this array.
    */
   List<JSONValue> values();

}
