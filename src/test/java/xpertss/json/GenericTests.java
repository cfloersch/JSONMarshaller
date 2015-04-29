/**
 * Created By: cfloersch
 * Date: 6/7/2014
 * Copyright 2013 XpertSoftware
 */
package xpertss.json;


import org.junit.Test;
import xpertss.json.generic.*;
import org.xpertss.json.util.Generics;

import java.lang.reflect.*;
import java.math.BigDecimal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;


public class GenericTests<T> {

   private Pair pair;
   private Left<Integer> left;
   private Right<Integer> right;
   private Tuple<String,Integer> tuple;




   @Test
   public void testPair() throws Exception
   {
      Field field = getClass().getDeclaredField("pair");
      Type type = field.getGenericType();
      assertFalse(Generics.isGeneric(type));
      Type next = Generics.resolveSuperTypes(type);
      assertFalse(Generics.isGeneric(next));
      assertSame(Left.class, Generics.extractRawType(next));
   }

   @Test
   public void testLeft() throws Exception
   {
      Field field = getClass().getDeclaredField("left");
      Type type = field.getGenericType();

      assertFalse(Generics.isGeneric(type));
      Type next = Generics.resolveSuperTypes(type);
      assertFalse(Generics.isGeneric(next));
      assertSame(Tuple.class, Generics.extractRawType(next));
   }


   @Test
   public void testRight() throws Exception
   {
      Field field = getClass().getDeclaredField("right");
      Type type = field.getGenericType();
      assertFalse(Generics.isGeneric(type));
      Type next = Generics.resolveSuperTypes(type);
      assertFalse(Generics.isGeneric(next));
      assertSame(Tuple.class, Generics.extractRawType(next));
   }

   @Test
   public void testTuple() throws Exception
   {
      Field field = getClass().getDeclaredField("tuple");
      Type type = field.getGenericType();
      assertFalse(Generics.isGeneric(type));
      Type next = Generics.resolveSuperTypes(type);
      assertFalse(Generics.isGeneric(next));
      assertSame(Object.class, next);
   }






   private Left<?> genericLeft;

   @Test
   public void testGenericLeft() throws Exception
   {
      Field field = getClass().getDeclaredField("genericLeft");
      Type type = field.getGenericType();

      assertTrue(Generics.isGeneric(type));
   }


   private Left undefinedLeft;

   @Test
   public void testUndefinedLeft() throws Exception
   {
      Field field = getClass().getDeclaredField("undefinedLeft");
      Type type = field.getGenericType();

      assertTrue(Generics.isGeneric(type));
   }





   private Triple triple;

   @Test
   public void testTriple() throws Exception
   {
      Field field = getClass().getDeclaredField("triple");
      Type type = field.getGenericType();
      assertFalse(Generics.isGeneric(type));

      Type next = Generics.resolveSuperTypes(type);
      assertTrue(Generics.isGeneric(next));

      assertSame(Tuple.class, Generics.extractRawType(next));
   }




   private T genericVar;

   @Test
   public void testGenericVar() throws Exception
   {
      Field field = getClass().getDeclaredField("genericVar");
      Type type = field.getGenericType();
      assertTrue(Generics.isGeneric(type));
      assertTrue(type instanceof TypeVariable);
   }










   private static void dump(String ident, Type type)
   {
      Class<?> klass = Generics.extractRawType(type);
      if(type instanceof Class) {
         System.out.println(ident);
         System.out.println("Type: " + type);
         dump(klass.getTypeParameters());
         System.out.println("SuperParams: " + klass.getGenericSuperclass());
         System.out.println("SuperArgs: " + Generics.resolveSuperTypes(type));
         System.out.println();
      } else if(type instanceof ParameterizedType) {
         System.out.println(ident);
         System.out.println("ParameterizedType: " + type);
         dump(klass.getTypeParameters());
         System.out.println("SuperParams: " + klass.getGenericSuperclass());
         System.out.println("SuperArgs: " + Generics.resolveSuperTypes(type));
         System.out.println();
      } else if(type instanceof GenericArrayType) {
         System.out.println(ident);
         System.out.println("GenericArrayType: " + type);
         dump(klass.getTypeParameters());
         System.out.println("SuperParams: " + klass.getGenericSuperclass());
         System.out.println("SuperArgs: " + Generics.resolveSuperTypes(type));
         System.out.println();
      }
   }

   private static void dump(Type[] params)
   {
      StringBuilder builder = new StringBuilder();
      for(Type param : params) {
         if(builder.length() > 0) builder.append(",");
         builder.append(param);
      }
      System.out.println("Class Params: <" + builder.append(">").toString());
   }




   @Test
   public void testLongToDouble()
   {
      BigDecimal value = BigDecimal.valueOf(Long.MAX_VALUE);
   }
}
