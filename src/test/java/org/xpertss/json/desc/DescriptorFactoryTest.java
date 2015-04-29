package org.xpertss.json.desc;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class DescriptorFactoryTest {

   private DescriptorFactory factory = new DescriptorFactory();
   

   // Map Tests
   private Map<String,Integer[]> genericIntegerArrayMap;
   private Map<String,Integer[][]> genericIntegerDoubleArrayMap;
   private Map<String,Integer>[] genericIntegerMapArray;
   private Map<String,Integer>[][] genericIntegerMapDoubleArray;


   @Test
   public void testGenericIntegerArrayMap() throws Exception
   {
      Field field = getClass().getDeclaredField("genericIntegerArrayMap");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("Map<IntegerDescriptor[]>", desc.toString());
   }

   @Test
   public void testGenericIntegerDoubleArrayMap() throws Exception
   {
      Field field = getClass().getDeclaredField("genericIntegerDoubleArrayMap");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("Map<IntegerDescriptor[][]>", desc.toString());
   }

   @Test
   public void testGenericIntegerMapArray() throws Exception
   {
      Field field = getClass().getDeclaredField("genericIntegerMapArray");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("Map<IntegerDescriptor>[]", desc.toString());
   }

   @Test
   public void testGenericIntegerMapDoubleArray() throws Exception
   {
      Field field = getClass().getDeclaredField("genericIntegerMapDoubleArray");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("Map<IntegerDescriptor>[][]", desc.toString());
   }






   private SortedMap<String,Integer[]> genericIntegerArraySortedMap;
   private SortedMap<String,Integer[][]> genericIntegerDoubleArraySortedMap;
   private SortedMap<String,Integer>[] genericIntegerSortedMapArray;
   private SortedMap<String,Integer>[][] genericIntegerSortedMapDoubleArray;


   @Test
   public void testGenericIntegerArraySortedMap() throws Exception
   {
      Field field = getClass().getDeclaredField("genericIntegerArraySortedMap");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("SortedMap<IntegerDescriptor[]>", desc.toString());
   }

   @Test
   public void testGenericIntegerDoubleArraySortedMap() throws Exception
   {
      Field field = getClass().getDeclaredField("genericIntegerDoubleArraySortedMap");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("SortedMap<IntegerDescriptor[][]>", desc.toString());
   }

   @Test
   public void testGenericIntegerSortedMapArray() throws Exception
   {
      Field field = getClass().getDeclaredField("genericIntegerSortedMapArray");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("SortedMap<IntegerDescriptor>[]", desc.toString());
   }

   @Test
   public void testGenericIntegerSortedMapDoubleArray() throws Exception
   {
      Field field = getClass().getDeclaredField("genericIntegerSortedMapDoubleArray");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("SortedMap<IntegerDescriptor>[][]", desc.toString());
   }









   private List<Integer[]> integerArrayList;
   private List<Integer[][]> integerDoubleArrayList;
   private List<Integer>[] integerListArray;
   private List<Integer>[][] integerListDoubleArray;


   @Test
   public void testIntegerArrayList() throws Exception
   {
      Field field = getClass().getDeclaredField("integerArrayList");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("List<IntegerDescriptor[]>", desc.toString());
   }

   @Test
   public void testIntegerDoubleArrayList() throws Exception
   {
      Field field = getClass().getDeclaredField("integerDoubleArrayList");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("List<IntegerDescriptor[][]>", desc.toString());
   }

   @Test
   public void testIntegerListArray() throws Exception
   {
      Field field = getClass().getDeclaredField("integerListArray");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("List<IntegerDescriptor>[]", desc.toString());
   }

   @Test
   public void testIntegerListDoubleArray() throws Exception
   {
      Field field = getClass().getDeclaredField("integerListDoubleArray");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("List<IntegerDescriptor>[][]", desc.toString());
   }








   // Set Tests
   private Set<Integer[]> integerArraySet;
   private Set<Integer[][]> integerDoubleArraySet;
   private Set<Integer>[] integerSetArray;
   private Set<Integer>[][] integerSetDoubleArray;


   @Test
   public void testIntegerArraSet() throws Exception
   {
      Field field = getClass().getDeclaredField("integerArraySet");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("Set<IntegerDescriptor[]>", desc.toString());
   }

   @Test
   public void testIntegerDoubleArraySet() throws Exception
   {
      Field field = getClass().getDeclaredField("integerDoubleArraySet");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("Set<IntegerDescriptor[][]>", desc.toString());
   }

   @Test
   public void testIntegerSEtArray() throws Exception
   {
      Field field = getClass().getDeclaredField("integerSetArray");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("Set<IntegerDescriptor>[]", desc.toString());
   }

   @Test
   public void testIntegerSetDoubleArray() throws Exception
   {
      Field field = getClass().getDeclaredField("integerSetDoubleArray");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("Set<IntegerDescriptor>[][]", desc.toString());
   }






   private SortedSet<Integer[]> integerArraySortedSet;
   private SortedSet<Integer[][]> integerDoubleArraySortedSet;
   private SortedSet<Integer>[] integerSortedSetArray;
   private SortedSet<Integer>[][] integerSortedSetDoubleArray;



   @Test
   public void testIntegerArraSortedSet() throws Exception
   {
      Field field = getClass().getDeclaredField("integerArraySortedSet");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("SortedSet<IntegerDescriptor[]>", desc.toString());
   }

   @Test
   public void testIntegerDoubleArraySortedSet() throws Exception
   {
      Field field = getClass().getDeclaredField("integerDoubleArraySortedSet");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("SortedSet<IntegerDescriptor[][]>", desc.toString());
   }

   @Test
   public void testIntegerSortedSetArray() throws Exception
   {
      Field field = getClass().getDeclaredField("integerSortedSetArray");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("SortedSet<IntegerDescriptor>[]", desc.toString());
   }

   @Test
   public void testIntegerSortedSetDoubleArray() throws Exception
   {
      Field field = getClass().getDeclaredField("integerSortedSetDoubleArray");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("SortedSet<IntegerDescriptor>[][]", desc.toString());
   }










   // Collection Test
   private Collection<String> collection;

   @Test
   public void testCollection() throws Exception
   {
      Field field = getClass().getDeclaredField("collection");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("Collection<StringDescriptor>", desc.toString());
   }














   // Compound Generic Object Tests
   private Map<String,List<String>> mapListString;
   private Map<String,List<String>[]> mapListStringArray;
   private List<List<String>[][]> listOfListArrays;
   private List<List<String>[]>[] listArrayOfListArrays;

   @Test
   public void testMapListString() throws Exception
   {
      Field field = getClass().getDeclaredField("mapListString");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("Map<List<StringDescriptor>>", desc.toString());
   }

   @Test
   public void testMapListStringArray() throws Exception
   {
      Field field = getClass().getDeclaredField("mapListStringArray");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("Map<List<StringDescriptor>[]>", desc.toString());
   }

   @Test
   public void testListOfListArrays() throws Exception
   {
      Field field = getClass().getDeclaredField("listOfListArrays");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("List<List<StringDescriptor>[][]>", desc.toString());
   }

   @Test
   public void testListArrayOfListArrays() throws Exception
   {
      Field field = getClass().getDeclaredField("listArrayOfListArrays");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("List<List<StringDescriptor>[]>[]", desc.toString());
   }



}