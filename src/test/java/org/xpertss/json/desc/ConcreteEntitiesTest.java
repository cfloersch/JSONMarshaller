package org.xpertss.json.desc;

import org.junit.Test;
import xpertss.json.book.Author;
import org.xpertss.json.desc.entity.AbstractEntity;
import org.xpertss.json.desc.entity.ConflictSub;
import org.xpertss.json.desc.entity.InterfaceEntity;
import org.xpertss.json.desc.entity.NonBean;
import org.xpertss.json.desc.entity.NonEntity;
import org.xpertss.json.desc.entity.SubClass;
import xpertss.json.generic.Pair;
import xpertss.json.generic.Left;
import xpertss.json.generic.Right;
import xpertss.json.generic.Tuple;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by cfloersch on 6/12/2014.
 */
public class ConcreteEntitiesTest {

   private DescriptorFactory factory = new DescriptorFactory();



   // Entity Field Tests
   private Author author;

   @Test
   public void testEntityField() throws Exception
   {
      Field field = getClass().getDeclaredField("author");
      Descriptor desc = factory.create(field.getGenericType());
      assertSame(ConcreteEntityDescriptor.class, desc.getClass());
      assertSame(Author.class, desc.getReturnedClass());
   }




   // Entity Tests

   @Test
   public void testSimpleConcreteEntity() throws Exception {
      EntityDescriptor<Author> desc = factory.create(Author.class);
      Set<FieldDescriptor> fields = desc.getFieldDescriptors();
      assertEquals(2, fields.size());
      assertTrue(contains(fields, "firstName"));
      assertTrue(contains(fields, "lastName"));
   }

   @Test
   public void testInheritanceEntity()
   {
      EntityDescriptor<SubClass> desc = factory.create(SubClass.class);
      Set<FieldDescriptor> fields = desc.getFieldDescriptors();
      assertEquals(2, fields.size());
      assertTrue(contains(fields, "age"));
      assertTrue(contains(fields, "name"));
   }


   @Test(expected = IllegalArgumentException.class)
   public void testNonBeanEntity()
   {
      // Doesn't like beans missing zero argument constructors
      factory.create(NonBean.class);
   }

   @Test(expected = IllegalArgumentException.class)
   public void testNonEntity()
   {
      // Doesn't like classes which do not have the Entity annotation
      factory.create(NonEntity.class);
   }

   @Test(expected = IllegalArgumentException.class)
   public void testEntityWithNameConflict()
   {
      // Does not like Entities that have a field name conflict with a super class
      factory.create(ConflictSub.class);
   }


   @Test(expected = IllegalArgumentException.class)
   public void testAbstractEntity()
   {
      factory.create(AbstractEntity.class);
   }

   @Test(expected = IllegalArgumentException.class)
   public void testInterfaceEntity()
   {
      factory.create(InterfaceEntity.class);
   }






   // entities with generics and fields with generics
   private Tuple<String,Integer> tuple;
   private Left<Integer> left;
   private Right<Integer> right;
   private Pair pair;

   @Test
   public void testTuple() throws Exception
   {
      Field field = getClass().getDeclaredField("tuple");
      EntityDescriptor desc = (EntityDescriptor) factory.create(field.getGenericType());
      Set<FieldDescriptor> fields = desc.getFieldDescriptors();
      assertEquals(2, fields.size());
      FieldDescriptor left = Objects.requireNonNull(get(fields, "left"));
      assertEquals("left as \"left\": StringDescriptor", left.toString());
      FieldDescriptor right = Objects.requireNonNull(get(fields, "right"));
      assertEquals("right as \"right\": IntegerDescriptor", right.toString());
   }

   @Test
   public void testLeft() throws Exception
   {
      Field field = getClass().getDeclaredField("left");
      EntityDescriptor desc = (EntityDescriptor) factory.create(field.getGenericType());
      Set<FieldDescriptor> fields = desc.getFieldDescriptors();
      assertEquals(2, fields.size());
      FieldDescriptor left = Objects.requireNonNull(get(fields, "left"));
      assertEquals("left as \"left\": StringDescriptor", left.toString());
      FieldDescriptor right = Objects.requireNonNull(get(fields, "right"));
      assertEquals("right as \"right\": IntegerDescriptor", right.toString());
   }

   @Test
   public void testRight() throws Exception
   {
      Field field = getClass().getDeclaredField("right");
      EntityDescriptor desc = (EntityDescriptor) factory.create(field.getGenericType());
      Set<FieldDescriptor> fields = desc.getFieldDescriptors();
      assertEquals(2, fields.size());
      FieldDescriptor left = Objects.requireNonNull(get(fields, "left"));
      assertEquals("left as \"left\": IntegerDescriptor", left.toString());
      FieldDescriptor right = Objects.requireNonNull(get(fields, "right"));
      assertEquals("right as \"right\": StringDescriptor", right.toString());
   }

   @Test
   public void testPair() throws Exception
   {
      Field field = getClass().getDeclaredField("pair");
      EntityDescriptor desc = (EntityDescriptor) factory.create(field.getGenericType());
      Set<FieldDescriptor> fields = desc.getFieldDescriptors();
      assertEquals(2, fields.size());
      FieldDescriptor left = Objects.requireNonNull(get(fields, "left"));
      assertEquals("left as \"left\": StringDescriptor", left.toString());
      FieldDescriptor right = Objects.requireNonNull(get(fields, "right"));
      assertEquals("right as \"right\": StringDescriptor", right.toString());
   }






   private Right<?> wildCard;

   @Test(expected = IllegalArgumentException.class)
   public void testEntityWithWildcardGeneric() throws Exception
   {
      Field field = getClass().getDeclaredField("wildCard");
      factory.create(field.getGenericType());
   }







   // Utility test method
   private static boolean contains(Set<FieldDescriptor> fields, String name)
   {
      for(FieldDescriptor field : fields) {
         if(name.equals(field.getFieldName().getString())) return true;
      }
      return false;
   }

   private static FieldDescriptor get(Set<FieldDescriptor> fields, String name)
   {
      for(FieldDescriptor field : fields) {
         if(name.equals(field.getFieldName().getString())) return field;
      }
      return null;
   }

}
