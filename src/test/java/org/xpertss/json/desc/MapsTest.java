package org.xpertss.json.desc;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

import static org.junit.Assert.assertEquals;

/**
 * Created by cfloersch on 6/12/2014.
 */
public class MapsTest {

   private DescriptorFactory factory = new DescriptorFactory();

   // Map Tests
   private Map nonGenericMap;
   private Map<Object,String> invalidGenericMap;
   private Map<String,?> wildcardGenericMap;
   private Map<String,String> genericStringMap;
   private Map<String,Integer> genericIntegerMap;


   @Test(expected = IllegalArgumentException.class)
   public void testNonGenericMap() throws Exception
   {
      Field field = getClass().getDeclaredField("nonGenericMap");
      factory.create(field.getGenericType());
   }

   @Test(expected = IllegalArgumentException.class)
   public void testInvalidGenericMap() throws Exception
   {
      Field field = getClass().getDeclaredField("invalidGenericMap");
      factory.create(field.getGenericType());
   }

   @Test(expected = IllegalArgumentException.class)
   public void testWildcardGenericMap() throws Exception
   {
      Field field = getClass().getDeclaredField("wildcardGenericMap");
      factory.create(field.getGenericType());
   }

   @Test
   public void testGenericStringMap() throws Exception
   {
      Field field = getClass().getDeclaredField("genericStringMap");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("Map<StringDescriptor>", desc.toString());
   }

   @Test
   public void testGenericIntegerMap() throws Exception
   {
      Field field = getClass().getDeclaredField("genericIntegerMap");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("Map<IntegerDescriptor>", desc.toString());
   }


   private Map<Long,Long> longMap;

   @Test(expected = IllegalArgumentException.class)
   public void testLongKeyedMap() throws Exception
   {
      Field field = getClass().getDeclaredField("longMap");
      factory.create(field.getGenericType());
   }



   // Concrete Map Tests
   private HashMap<String,String> hashMap;
   private LinkedHashMap<String,String> linkedHashMap;
   private WeakHashMap<String,String> weakHashMap;
   private IdentityHashMap<String,String> identHashMap;
   private ConcurrentMap<String,String> concurrentMap;
   private ConcurrentHashMap<String,String> concurrentHashMap;

   @Test
   public void testHashMap() throws Exception
   {
      Field field = getClass().getDeclaredField("hashMap");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("HashMap<StringDescriptor>", desc.toString());
   }

   @Test
   public void testLinkedHashMap() throws Exception
   {
      Field field = getClass().getDeclaredField("linkedHashMap");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("LinkedHashMap<StringDescriptor>", desc.toString());
   }

   @Test
   public void testWeakHashMap() throws Exception
   {
      Field field = getClass().getDeclaredField("weakHashMap");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("WeakHashMap<StringDescriptor>", desc.toString());
   }

   @Test
   public void testIdentityHashMap() throws Exception
   {
      Field field = getClass().getDeclaredField("identHashMap");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("IdentityHashMap<StringDescriptor>", desc.toString());
   }

   @Test
   public void testConcurrentMap() throws Exception
   {
      Field field = getClass().getDeclaredField("concurrentMap");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("ConcurrentMap<StringDescriptor>", desc.toString());
   }

   @Test
   public void testConcurrentHashMap() throws Exception
   {
      Field field = getClass().getDeclaredField("concurrentHashMap");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("ConcurrentHashMap<StringDescriptor>", desc.toString());
   }


   // Sorted Map Tests
   private SortedMap nonGenericSortedMap;
   private SortedMap<Object,String> invalidGenericSortedMap;
   private SortedMap<String,String> genericStringSortedMap;
   private SortedMap<String,Integer> genericIntegerSortedMap;

   @Test(expected = IllegalArgumentException.class)
   public void testNonGenericSortedMap() throws Exception
   {
      Field field = getClass().getDeclaredField("nonGenericSortedMap");
      factory.create(field.getGenericType());
   }

   @Test(expected = IllegalArgumentException.class)
   public void testInvalidGenericSortedMap() throws Exception
   {
      Field field = getClass().getDeclaredField("invalidGenericSortedMap");
      factory.create(field.getGenericType());
   }

   @Test
   public void testGenericStringSortedMap() throws Exception
   {
      Field field = getClass().getDeclaredField("genericStringSortedMap");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("SortedMap<StringDescriptor>", desc.toString());
   }

   @Test
   public void testGenericIntegerSortedMap() throws Exception
   {
      Field field = getClass().getDeclaredField("genericIntegerSortedMap");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("SortedMap<IntegerDescriptor>", desc.toString());
   }



   // Concrete SortedMap Tests
   private ConcurrentSkipListMap<String,String> skipListMap;
   private TreeMap<String,String> treeMap;

   @Test
   public void testConcurrentSkipListMap() throws Exception
   {
      Field field = getClass().getDeclaredField("skipListMap");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("ConcurrentSkipListMap<StringDescriptor>", desc.toString());
   }

   @Test
   public void testTreeMap() throws Exception
   {
      Field field = getClass().getDeclaredField("treeMap");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("TreeMap<StringDescriptor>", desc.toString());
   }



   // Concrete NavigableMap Tests
   private NavigableMap<String,String> navigableMap;
   private ConcurrentNavigableMap<String,String> concurrentNavigableMap;

   @Test
   public void testNavigableMap() throws Exception
   {
      Field field = getClass().getDeclaredField("navigableMap");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("NavigableMap<StringDescriptor>", desc.toString());
   }

   @Test
   public void testConcurrentNavigableMap() throws Exception
   {
      Field field = getClass().getDeclaredField("concurrentNavigableMap");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("ConcurrentNavigableMap<StringDescriptor>", desc.toString());
   }






   private MyMap myMap;

   @Test(expected = IllegalArgumentException.class)
   public void testMyMap() throws Exception
   {
      Field field = getClass().getDeclaredField("myMap");
      factory.create(field.getGenericType());
   }




   private static interface MyMap extends Map<String,String> {

   }
}
