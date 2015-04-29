package org.xpertss.json.desc;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by cfloersch on 6/12/2014.
 */
public class ListsTest {

   private DescriptorFactory factory = new DescriptorFactory();

   // List Tests
   private List list;
   private List<?> genericList;
   private List<String> stringList;
   private List<Integer> integerList;


   @Test(expected = IllegalArgumentException.class)
   public void testNonGenericList() throws Exception
   {
      Field field = getClass().getDeclaredField("list");
      factory.create(field.getGenericType());
   }

   @Test(expected = IllegalArgumentException.class)
   public void testWildcardList() throws Exception
   {
      Field field = getClass().getDeclaredField("genericList");
      factory.create(field.getGenericType());
   }

   @Test
   public void testStringList() throws Exception
   {
      Field field = getClass().getDeclaredField("stringList");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("List<StringDescriptor>", desc.toString());
   }

   @Test
   public void testIntegerList() throws Exception
   {
      Field field = getClass().getDeclaredField("integerList");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("List<IntegerDescriptor>", desc.toString());
   }




   // Concrete List Tests
   private ArrayList<String> arrayList;
   private LinkedList<String> linkedList;
   private CopyOnWriteArrayList<String> concurrentList;

   @Test
   public void testArrayList() throws Exception
   {
      Field field = getClass().getDeclaredField("arrayList");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("ArrayList<StringDescriptor>", desc.toString());
   }

   @Test
   public void testLinkedList() throws Exception
   {
      Field field = getClass().getDeclaredField("linkedList");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("LinkedList<StringDescriptor>", desc.toString());
   }

   @Test
   public void testLConcurrentList() throws Exception
   {
      Field field = getClass().getDeclaredField("concurrentList");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("CopyOnWriteArrayList<StringDescriptor>", desc.toString());
   }



   private MyList myList;

   @Test(expected = IllegalArgumentException.class)
   public void testMyList() throws Exception
   {
      Field field = getClass().getDeclaredField("myList");
      factory.create(field.getGenericType());
   }



   private interface MyList extends List<String> {

   }
}
