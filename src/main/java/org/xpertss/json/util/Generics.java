package org.xpertss.json.util;


import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class Generics {

   private Generics() { }


   /**
    * Returns {@code true} if the given type does not represent a fully
    * defined type, {@code false} otherwise.
    * <p/>
    * If the type represents a class then it returns {@code true} if the
    * class signature defines any type parameters.
    * <p/>
    * if the type is a ParameterizedType then it returns {@code true} if
    * any of the class' type parameters is matched to a wildcard type.
    * <p/>
    * If the type is a GenericArrayType then it returns {@code false}.
    * <p/>
    * Any other type will return {@code true}.
    *
    * @throws NullPointerException If the specified type is {@code null}
    */
   public static boolean isGeneric(Type t)
   {
      if(t == null) {
         throw new NullPointerException();
      } else if(t instanceof Class) {
         return ((Class)t).getTypeParameters().length != 0;
      } else if(t instanceof ParameterizedType) {
         ParameterizedType pt = (ParameterizedType) t;
         Type[] types = extractRawType(t).getTypeParameters();
         for(int i = 0; i < types.length; i++) {
            Type arg = pt.getActualTypeArguments()[i];
            if(arg instanceof WildcardType) return true;
         }
         return false;
      } else if(t instanceof GenericArrayType) {
         return false;
      }
      return true;
   }

   /**
    * Returns {@code true} if the type argument at the specified index is
    * equal to the specified class. This does not support subclasses of the
    * specified class type.
    */
   public static boolean isType(ParameterizedType type, int index, Class<?> cls)
   {
      Type[] types = type.getActualTypeArguments();
      return (types != null && types.length > index) && types[index] == cls;
   }



   @SuppressWarnings("unchecked")
   public static Class<?> extractRawType(Type type)
   {
      if(type instanceof ParameterizedType) {
         return (Class) (((ParameterizedType) type).getRawType());
      } else if(type instanceof GenericArrayType) {
         return Array.class;
      } else {
         return (Class) type;
      }
   }






   public static Type resolveSuperTypes(Type sub)
   {
      Class<?> c = extractRawType(sub);
      Type sup = c.getGenericSuperclass();
      if(sub instanceof ParameterizedType) {
         if(sup instanceof ParameterizedType) {
            Map<TypeVariable,Type> mappings = mapTypeVars((ParameterizedType) sub);
            ParameterizedType params = (ParameterizedType) sup;
            List<Type> results = new ArrayList<>();
            for(Type t : params.getActualTypeArguments()) {
               if(t instanceof TypeVariable) {
                  results.add(mappings.get(t));
               } else {
                  results.add(t);
               }
            }
            return new BasicParameterizedType((Class<?>)params.getRawType(), results);
         }
      }
      return sup;
   }

   public static Map<TypeVariable,Type> mapTypeVars(ParameterizedType args)
   {
      Map<TypeVariable,Type> mappings = new LinkedHashMap<>();
      TypeVariable[] params = extractRawType(args).getTypeParameters();
      for(int i = 0; i < params.length; i++) {
         mappings.put(params[i], args.getActualTypeArguments()[i]);
      }
      return mappings;
   }





   private static class BasicParameterizedType implements ParameterizedType {
      private Type[] types;
      private Class<?> klass;
      private BasicParameterizedType(Class<?> klass, List<Type> types)
      {
         this.klass = klass;
         this.types = types.toArray(new Type[types.size()]);
      }

      @Override
      public Type[] getActualTypeArguments()
      {
         return types;
      }

      @Override
      public Type getRawType()
      {
         return klass;
      }

      @Override
      public Type getOwnerType()
      {
         return null;
      }

      @Override
      public String toString()
      {
         StringBuilder builder = new StringBuilder();
         builder.append(klass.getName()).append("<");
         for(int i = 0; i < types.length; i++) {
            if(i > 0) builder.append(", ");
            builder.append(types[i]);
         }
         return builder.append(">").toString();
      }
   }



}

