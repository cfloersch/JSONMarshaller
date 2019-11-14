package xpertss.json.desc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

import static java.lang.String.format;

/**
 * An enumeration of supported concrete collection types.
 */
public enum CollectionType {

   /**
    * TreeSet support. Default SortedSet type.
    *
    * @see java.util.SortedSet
    * @see java.util.TreeSet
    */
   TreeSet
      {
         @Override
         public <T> Collection<T> newCollection() { return new TreeSet<T>(); }

         @Override
         public Class<? extends Collection> toClass() { return TreeSet.class; }
      },


   /**
    * LinkedHashSet support. Default Set type.
    *
    * @see java.util.Set
    * @see java.util.LinkedHashSet
    */
   LinkedHashSet
      {
         @Override
         public <T> Collection<T> newCollection() { return new LinkedHashSet<T>(); }

         @Override
         public Class<? extends Collection> toClass() { return LinkedHashSet.class; }
      },


   /**
    * HashSet support. Not used as default for anything.
    *
    * @see java.util.HashSet
    */
   HashSet
      {
         @Override
         public <T> Collection<T> newCollection() { return new HashSet<T>(); }

         @Override
         public Class<? extends Collection> toClass() { return HashSet.class; }
      },

   /**
    * CopyOnWriteArraySet support. Not used as default for anything.
    *
    * @see java.util.concurrent.CopyOnWriteArraySet
    *
    */
   CopyOnWriteArraySet
      {
         @Override
         public <T> Collection<T> newCollection() { return new CopyOnWriteArraySet<T>(); }

         @Override
         public Class<? extends Collection> toClass() { return CopyOnWriteArraySet.class; }
      },

   /**
    * ConcurrentSkipListSet support. Not used as default for anything.
    *
    * @see java.util.concurrent.ConcurrentSkipListSet
    *
    */
   ConcurrentSkipListSet
      {
         @Override
         public <T> Collection<T> newCollection() { return new ConcurrentSkipListSet<T>(); }

         @Override
         public Class<? extends Collection> toClass() { return ConcurrentSkipListSet.class; }
      },



   /**
    * ArrayList support. Default for both List and Collection types.
    *
    * @see java.util.List
    * @see java.util.Collection
    * @see java.util.ArrayList
    */
   ArrayList
      {
         @Override
         public <T> Collection<T> newCollection() { return new ArrayList<T>(); }

         @Override
         public Class<? extends Collection> toClass() { return ArrayList.class; }
      },

   /**
    * LinkedList support. Not used as default for anything.
    *
    * @see java.util.LinkedList
    */
   LinkedList
      {
         @Override
         public <T> Collection<T> newCollection() { return new LinkedList<T>(); }

         @Override
         public Class<? extends Collection> toClass() { return LinkedList.class; }
      },

   /**
    * CopyOnWriteArrayList support. Not used as default for anything.
    *
    * @see java.util.concurrent.CopyOnWriteArrayList
    */
   CopyOnWriteArrayList
      {
         @Override
         public <T> Collection<T> newCollection() { return new CopyOnWriteArrayList<T>(); }

         @Override
         public Class<? extends Collection> toClass() { return CopyOnWriteArrayList.class; }
      };


   /**
    * Returns a new instance of the collection type.
    *
    * @param <T> the type of collection values
    * @return a new collection instance
    */
   public abstract <T> Collection<T> newCollection();

   /**
    * Returns the Class type of the collection type.
    *
    * @return the class type of returned collection instances
    */
   public abstract Class<? extends Collection> toClass();




   /**
    * Return the appropriate CollectionType for the given class.
    *
    * @param klass the class to evaluate
    * @return the collection type for the given class
    */
   public static CollectionType fromClass(Class<? extends Collection> klass)
   {
      if(klass == TreeSet.class) {
         return TreeSet;
      } else if(klass == ConcurrentSkipListSet.class) {
         return ConcurrentSkipListSet;
      } else if(klass == CopyOnWriteArraySet.class) {
         return CopyOnWriteArraySet;
      } else if(klass == LinkedHashSet.class) {
         return LinkedHashSet;
      } else if(klass == HashSet.class) {
         return HashSet;
      } else if(klass == ArrayList.class) {
         return ArrayList;
      } else if(klass == LinkedList.class) {
         return LinkedList;
      } else if(klass == CopyOnWriteArrayList.class) {
         return CopyOnWriteArrayList;

      } else if(klass == Set.class) {
         return setType;
      } else if(klass == List.class) {
         return listType;
      } else if(klass == NavigableSet.class) {
         return navigableSetType;
      } else if(klass == SortedSet.class) {
         return sortedSetType;
      } else if(klass == Collection.class) {
         return collectionType;
      }
      throw new IllegalArgumentException(format("%s is not a supported collection type", klass.getSimpleName()));
   }




   private static CollectionType collectionType = ArrayList;
   private static CollectionType listType = ArrayList;
   private static CollectionType setType = LinkedHashSet;
   private static CollectionType sortedSetType = TreeSet;
   private static CollectionType navigableSetType = TreeSet;

   /**
    * Set the default concrete collection type to use when creating instances
    * of {@link java.util.Collection}.
    *
    * @param type the collection type to use by default
    * @throws NullPointerException If type is {@code null}
    * @throws IllegalArgumentException If the specified type is not a
    *    subclass of Collection
    */
   public static void setDefaultCollectionType(CollectionType type)
   {
      if(Collection.class.isAssignableFrom(type.toClass())) {
         collectionType = type;
      } else {
         throw new IllegalArgumentException("not a collection subtype");
      }
   }

   /**
    * Set the default concrete collection type to use when creating instances
    * of {@link java.util.List}.
    *
    * @param type the list type to use by default
    * @throws NullPointerException If type is {@code null}
    * @throws IllegalArgumentException If the specified type is not a
    *    subclass of List
    */
   public static void setDefaultListType(CollectionType type)
   {
      if(List.class.isAssignableFrom(type.toClass())) {
         listType = type;
      } else {
         throw new IllegalArgumentException("not a list subtype");
      }
   }

   /**
    * Set the default concrete collection type to use when creating instances
    * of {@link java.util.Set}.
    *
    * @param type the set type to use by default
    * @throws NullPointerException If type is {@code null}
    * @throws IllegalArgumentException If the specified type is not a
    *    subclass of Set
    */
   public static void setDefaultSetType(CollectionType type)
   {
      if(Set.class.isAssignableFrom(type.toClass())) {
         setType = type;
      } else {
         throw new IllegalArgumentException("not a set subtype");
      }
   }

   /**
    * Set the default concrete collection type to use when creating instances
    * of {@link java.util.SortedSet}.
    *
    * @param type the sorted set type to use by default
    * @throws NullPointerException If type is {@code null}
    * @throws IllegalArgumentException If the specified type is not a
    *    subclass of SortedSet
    */
   public static void setDefaultSortedSetType(CollectionType type)
   {
      if(SortedSet.class.isAssignableFrom(type.toClass())) {
         sortedSetType = type;
      } else {
         throw new IllegalArgumentException("not a sorted set subtype");
      }
   }

   /**
    * Set the default concrete collection type to use when creating instances
    * of {@link java.util.NavigableSet}.
    *
    * @param type the navigable set type to use by default
    * @throws NullPointerException If type is {@code null}
    * @throws IllegalArgumentException If the specified type is not a
    *    subclass of NavigableSet
    */
   public static void setDefaultNavigableSetType(CollectionType type)
   {
      if(NavigableSet.class.isAssignableFrom(type.toClass())) {
         sortedSetType = type;
      } else {
         throw new IllegalArgumentException("not a navigable set subtype");
      }
   }

}
