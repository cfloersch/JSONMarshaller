package xpertss.json.desc;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

import static java.lang.String.format;

/**
 * An enumeration of supported map types.
 */
public enum MapType {

   /**
    * HashMap support. Not default for anything.
    *
    * @see java.util.HashMap
    */
   HashMap
      {
         @Override
         public <K, V> Map<K, V> newMap() { return new HashMap<K, V>(); }

         @Override
         public Class<? extends Map> toClass() { return HashMap.class; }
      },

   /**
    * IdentityHashMap support. Not default for anything.
    *
    * @see java.util.IdentityHashMap
    */
   IdentityHashMap
      {
         @Override
         public <K, V> Map<K, V> newMap() { return new IdentityHashMap<K, V>(); }

         @Override
         public Class<? extends Map> toClass()
         {
            return IdentityHashMap.class;
         }
      },

   /**
    * LinkedHashMap support. Default for Map.
    *
    * @see java.util.Map
    * @see java.util.LinkedHashMap
    */
   LinkedHashMap
      {
         @Override
         public <K, V> Map<K, V> newMap() { return new LinkedHashMap<K, V>(); }

         @Override
         public Class<? extends Map> toClass()
         {
            return LinkedHashMap.class;
         }
      },

   /**
    * WeakHashMap support. Not default for anything.
    *
    * @see java.util.WeakHashMap
    */
   WeakHashMap
      {
         @Override
         public <K, V> Map<K, V> newMap() { return new WeakHashMap<K, V>(); }

         @Override
         public Class<? extends Map> toClass()
         {
            return WeakHashMap.class;
         }
      },

   /**
    * ConcurrentHashMap support. Default for ConcurrentMap
    *
    * @see java.util.concurrent.ConcurrentMap
    * @see java.util.concurrent.ConcurrentHashMap
    */
   ConcurrentHashMap
      {
         @Override
         public <K, V> Map<K, V> newMap() {
            return new ConcurrentHashMap<K, V>();
         }

         @Override
         public Class<? extends Map> toClass()
         {
            return ConcurrentHashMap.class;
         }
      },


   /**
    * TreeMap support. Default for SortedMap
    *
    * @see java.util.SortedMap
    * @see java.util.TreeMap
    */
   TreeMap
      {
         @Override
         public <K, V> Map<K, V> newMap() { return new TreeMap<K, V>(); }

         @Override
         public Class<? extends Map> toClass() { return TreeMap.class; }
      },

   /**
    * ConcurrentSkipListMap support. Default for ConcurrentNavigableMap.
    *
    * @see java.util.concurrent.ConcurrentNavigableMap
    * @see java.util.concurrent.ConcurrentSkipListMap
    */
   ConcurrentSkipListMap
      {
         @Override
         public <K, V> Map<K, V> newMap() { return new ConcurrentSkipListMap<K, V>(); }

         @Override
         public Class<? extends Map> toClass() { return ConcurrentSkipListMap.class; }
      };



   /**
    * Returns a new instance of the map type.
    */
   public abstract <K, V> Map<K, V> newMap();

   /**
    * Returns the Class type of the map type.
    */
   public abstract Class<? extends Map> toClass();


   /**
    * Return the appropriate MapType for the given class.
    */
   public static MapType fromClass(Class<? extends Map> klass)
   {
      if(klass == WeakHashMap.class) {
         return WeakHashMap;
      } else if(klass == IdentityHashMap.class) {
         return IdentityHashMap;
      } else if(klass == LinkedHashMap.class) {
         return LinkedHashMap;
      } else if(klass == ConcurrentHashMap.class) {
         return ConcurrentHashMap;
      } else if(klass == HashMap.class) {
         return HashMap;
      } else if(klass == ConcurrentSkipListMap.class) {
         return ConcurrentSkipListMap;
      } else if(klass == TreeMap.class) {
         return TreeMap;

      } else if(klass == ConcurrentNavigableMap.class) {
         return concurrentNavigableMapType;
      } else if(klass == SortedMap.class) {
         return sortedMapType;
      } else if(klass == NavigableMap.class) {
         return navigableMapType;
      } else if(klass == ConcurrentMap.class) {
         return concurrentMapType;
      } else if(klass == Map.class) {
         return mapType;
      }
      throw new IllegalArgumentException(format("%s is not a supported map type", klass.getSimpleName()));

   }


   private static MapType mapType = LinkedHashMap;
   private static MapType sortedMapType = TreeMap;
   private static MapType navigableMapType = TreeMap;

   private static MapType concurrentMapType = ConcurrentHashMap;
   private static MapType concurrentNavigableMapType = ConcurrentSkipListMap;


   /**
    * Set the default concrete map type to use when creating instances of
    * {@link java.util.Map}.
    *
    * @throws NullPointerException If type is {@code null}
    * @throws IllegalArgumentException If the specified type is not a
    *    subclass of Map
    */
   public static void setDefaultMapType(MapType type)
   {
      if(Map.class.isAssignableFrom(type.toClass())) {
         mapType = type;
      } else {
         throw new IllegalArgumentException("not a map subtype");
      }
   }

   /**
    * Set the default concrete map type to use when creating instances of
    * {@link java.util.SortedMap}.
    *
    * @throws NullPointerException If type is {@code null}
    * @throws IllegalArgumentException If the specified type is not a
    *    subclass of SortedMap
    */
   public static void setDefaultSortedMapType(MapType type)
   {
      if(SortedMap.class.isAssignableFrom(type.toClass())) {
         sortedMapType = type;
      } else {
         throw new IllegalArgumentException("not a sorted map subtype");
      }
   }

   /**
    * Set the default concrete map type to use when creating instances of
    * {@link java.util.NavigableMap}.
    *
    * @throws NullPointerException If type is {@code null}
    * @throws IllegalArgumentException If the specified type is not a
    *    subclass of NavigableMap
    */
   public static void setDefaultNavigableMapType(MapType type)
   {
      if(NavigableMap.class.isAssignableFrom(type.toClass())) {
         navigableMapType = type;
      } else {
         throw new IllegalArgumentException("not a navigable map subtype");
      }

   }

   /**
    * Set the default concrete map type to use when creating instances of
    * {@link java.util.concurrent.ConcurrentMap}.
    *
    * @throws NullPointerException If type is {@code null}
    * @throws IllegalArgumentException If the specified type is not a
    *    subclass of ConcurrentMap
    */
   public static void setDefaultConcurrentMapType(MapType type)
   {
      if(ConcurrentMap.class.isAssignableFrom(type.toClass())) {
         concurrentMapType = type;
      } else {
         throw new IllegalArgumentException("not a concurrent map subtype");
      }
   }

   /**
    * Set the default concrete map type to use when creating instances of
    * {@link java.util.concurrent.ConcurrentNavigableMap}.
    *
    * @throws NullPointerException If type is {@code null}
    * @throws IllegalArgumentException If the specified type is not a
    *    subclass of ConcurrentNavigableMap
    */
   public static void setDefaultConcurrentNavigableMapType(MapType type)
   {
      if(ConcurrentNavigableMap.class.isAssignableFrom(type.toClass())) {
         concurrentNavigableMapType = type;
      } else {
         throw new IllegalArgumentException("not a concurrent navigable map subtype");
      }
   }


}
