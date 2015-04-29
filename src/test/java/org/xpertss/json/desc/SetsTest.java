package org.xpertss.json.desc;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;

import static org.junit.Assert.assertEquals;

/**
 * Created by cfloersch on 6/12/2014.
 */
public class SetsTest {

   private DescriptorFactory factory = new DescriptorFactory();

   // Set Tests
   private Set set;
   private Set<String> stringSet;
   private Set<Integer> integerSet;

   @Test(expected = IllegalArgumentException.class)
   public void testNonGenericSet() throws Exception
   {
      Field field = getClass().getDeclaredField("set");
      factory.create(field.getGenericType());
   }

   @Test
   public void testStringSet() throws Exception
   {
      Field field = getClass().getDeclaredField("stringSet");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("Set<StringDescriptor>", desc.toString());
   }

   @Test
   public void testIntegerSet() throws Exception
   {
      Field field = getClass().getDeclaredField("integerSet");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("Set<IntegerDescriptor>", desc.toString());
   }


   // Sorted Set Tests
   private SortedSet sortedSet;
   private SortedSet<String> stringSortedSet;
   private SortedSet<Integer> integerSortedSet;

   @Test(expected = IllegalArgumentException.class)
   public void testNonGenericSortedSet() throws Exception
   {
      Field field = getClass().getDeclaredField("sortedSet");
      factory.create(field.getGenericType());
   }

   @Test
   public void testStringSortedSet() throws Exception
   {
      Field field = getClass().getDeclaredField("stringSortedSet");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("SortedSet<StringDescriptor>", desc.toString());
   }

   @Test
   public void testIntegerSortedSet() throws Exception
   {
      Field field = getClass().getDeclaredField("integerSortedSet");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("SortedSet<IntegerDescriptor>", desc.toString());
   }



   // Concrete Set Tests
   private HashSet<String> hashSet;
   private LinkedHashSet<String> linkedHashSet;
   private CopyOnWriteArraySet<String> concurrentSet;

   @Test
   public void testHashSet() throws Exception
   {
      Field field = getClass().getDeclaredField("hashSet");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("HashSet<StringDescriptor>", desc.toString());
   }

   @Test
   public void testLinkedHashSet() throws Exception
   {
      Field field = getClass().getDeclaredField("linkedHashSet");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("LinkedHashSet<StringDescriptor>", desc.toString());
   }

   @Test
   public void testConcurrentSet() throws Exception
   {
      Field field = getClass().getDeclaredField("concurrentSet");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("CopyOnWriteArraySet<StringDescriptor>", desc.toString());
   }




   //Concrete SortedSet Tests
   private ConcurrentSkipListSet<String> concurrentSortedSet;
   private TreeSet<String> treeSet;
   private NavigableSet<String> navigableSet;



   @Test
   public void testConcurrentSortedSet() throws Exception
   {
      Field field = getClass().getDeclaredField("concurrentSortedSet");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("ConcurrentSkipListSet<StringDescriptor>", desc.toString());
   }

   @Test
   public void testTreeSet() throws Exception
   {
      Field field = getClass().getDeclaredField("treeSet");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("TreeSet<StringDescriptor>", desc.toString());
   }

   @Test
   public void testNavigableSet() throws Exception
   {
      Field field = getClass().getDeclaredField("navigableSet");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("NavigableSet<StringDescriptor>", desc.toString());
   }



   private MySet mySet;

   @Test(expected = IllegalArgumentException.class)
   public void testMySet() throws Exception
   {
      Field field = getClass().getDeclaredField("mySet");
      factory.create(field.getGenericType());
   }



   private interface MySet extends Set<String> {

   }


}
