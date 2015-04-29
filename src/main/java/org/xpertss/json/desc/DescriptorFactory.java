/**
 * Created by IntelliJ IDEA.
 * User: cfloersch
 * Date: 4/14/11 9:02 AM
 * Copyright Manheim online
 */
package org.xpertss.json.desc;

import xpertss.json.Entity;
import xpertss.json.JSONObject;
import xpertss.json.JSONValue;
import xpertss.json.Value;
import xpertss.json.spi.JSONUserType;
import xpertss.json.spi.UserTypeService;
import org.xpertss.json.util.Generics;
import org.xpertss.json.util.ServiceLoader;
import org.xpertss.json.util.Strings;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import static java.lang.String.format;

public class DescriptorFactory {

   private final ServiceLoader<UserTypeService> userTypes = ServiceLoader.load(UserTypeService.class);



   public Descriptor<?, ?> create(Type g)
   {
      Cache cache = new Cache();
      return create(cache, g);
   }

   @SuppressWarnings("unchecked")
   Descriptor<?, ?> create(Cache cache, Type g)
   {
      if(Generics.isGeneric(g))
         throw new IllegalArgumentException("generic types must be fully defined");

      if (g instanceof Class) {
         Class<?> c = (Class<?>) g;
         if (c.isPrimitive()) {
            return createLiteral(c);
         } else if (c.isArray()) {
            return createArray(cache, g);
         } else if (c.isEnum()) {
            return new EnumDescriptor(c);
         } else {
            Descriptor result = createObject(c);
            if (result != null) return result;
            for (UserTypeService service : userTypes) {
               JSONUserType<?, ? extends JSONValue> type = service.create(g);
               if (type != null) return new UserTypeDescriptor(type);
            }
            return createEntity(cache, g);
         }
      } else if (g instanceof ParameterizedType) {
         ParameterizedType pt = (ParameterizedType) g;
         for (UserTypeService service : userTypes) {
            JSONUserType<?, ? extends JSONValue> type = service.create(g);
            if (type != null) return new UserTypeDescriptor(type);
         }
         Class<?> c = (Class<?>) pt.getRawType();
         if (Collection.class.isAssignableFrom(c)) {
            return createCollection(cache, pt);
         } else if (Map.class.isAssignableFrom(c)) {
            return createMap(cache, pt);
         }
         return createEntity(cache, g);
      } else if (g instanceof GenericArrayType) {
         return createArray(cache, g);
      }
      throw new IllegalArgumentException(format("unknown type %s", g));
   }



   // Creates descriptors for java literals such as char, int, short, boolean, etc
   Descriptor<?,?> createLiteral(Class<?> c)
   {
      if (c == Double.TYPE) {
         return DoubleDescriptor.DOUBLE_LITERAL_DESC;
      } else if (c == Float.TYPE) {
         return FloatDescriptor.FLOAT_LITERAL_DESC;
      } else if (c == Long.TYPE) {
         return LongDescriptor.LONG_LITERAL_DESC;
      } else if (c == Integer.TYPE) {
         return IntegerDescriptor.INT_LITERAL_DESC;
      } else if (c == Short.TYPE) {
         return ShortDescriptor.SHORT_LITERAL_DESC;
      } else if (c == Byte.TYPE) {
         return ByteDescriptor.BYTE_LITERAL_DESC;
      } else if (c == Boolean.TYPE) {
         return BooleanDescriptor.BOOLEAN_LITERAL_DESC;
      }
      return CharacterDescriptor.CHAR_LITERAL_DESC;
   }


   // Creates descriptors for java objects such as String, Short, Integer, Boolean, etc
   Descriptor<?,?> createObject(Class<?> c)
   {
      if (c == BigInteger.class) {
         return BigIntegerDescriptor.BIG_INTEGER_DESC;
      } else if (c == BigDecimal.class) {
         return BigDecimalDescriptor.BIG_DECIMAL_DESC;
      } else if (c == Double.class) {
         return DoubleDescriptor.DOUBLE_DESC;
      } else if (c == Float.class) {
         return FloatDescriptor.FLOAT_DESC;
      } else if (c == Long.class) {
         return LongDescriptor.LONG_DESC;
      } else if (c == Integer.class) {
         return IntegerDescriptor.INTEGER_DESC;
      } else if (c == Short.class) {
         return ShortDescriptor.SHORT_DESC;
      } else if (c == Byte.class) {
         return ByteDescriptor.BYTE_DESC;
      } else if (c == String.class) {
         return StringDescriptor.STRING_DESC;
      } else if (c == Character.class) {
         return CharacterDescriptor.CHARARACTER_DESC;
      } else if (c == Boolean.class) {
         return BooleanDescriptor.BOOLEAN_DESC;
      }
      return null;
   }


   // Internal method to create actual entity descriptors
   @SuppressWarnings("unchecked")
   EntityDescriptor<?> createEntity(Cache cache, Type g)
   {
      Class<?> c = Generics.extractRawType(g);

      Entity def = c.getAnnotation(Entity.class);
      if (def != null) {

         // I need to check if this entity is going to be polymorphic or not
         Class<?>[] subclasses = def.subclasses();
         if (subclasses.length > 0) {

            if(cache.contains(g)) return cache.get(g);

            String discriminatorName = def.discriminatorName();
            if (Strings.isEmpty(discriminatorName)) {
               throw new IllegalArgumentException(
                     "discriminatorName option must be used in conjunction with the " +
                           "subclasses option: " + c);

            }

            // getting all the concrete descriptors
            Set<EntityDescriptor<?>> subclassesDescriptors = new LinkedHashSet<>(subclasses.length);
            Set<String> discriminators = new LinkedHashSet<>(subclasses.length);

            for (Class<?> subclass : subclasses) {
               if (c.isAssignableFrom(subclass)) {
                  EntityDescriptor concreteEntity = createConcreteEntity(cache, remap(subclass, g));
                  String discriminator = concreteEntity.getDiscriminator();
                  if (!discriminators.add(discriminator)) {
                     throw new IllegalArgumentException(
                           "discriminator " + discriminator + " is already used by" +
                                 " the entity " + concreteEntity.getReturnedClass() + ".");
                  } else {
                     subclassesDescriptors.add(concreteEntity);
                  }
               } else {
                  throw new IllegalArgumentException(
                        "class " + subclass + " is not a subclass of the" +
                              " polymorphic entity " + c + ".");
               }
            }
            return cache.define(g, new PolymorphicEntityDescriptor(c, discriminatorName, subclassesDescriptors));
         }
         return createConcreteEntity(cache, g);
      }
      throw new IllegalArgumentException(format("class %s is not an entity", c.getSimpleName()));
   }


   @SuppressWarnings("unchecked")
   EntityDescriptor<?> createConcreteEntity(Cache cache, Type g)
   {

      Class<?> entity = Generics.extractRawType(g);

      // An entity passed here can not be abstract nor an interface.
      if (entity.isInterface()) {
         throw new IllegalArgumentException("concrete entities may not be interfaces");
      } else if (Modifier.isAbstract(entity.getModifiers())) {
         throw new IllegalArgumentException("concrete entities may not be abstract");
      }

      if(cache.contains(g)) return cache.get(g);

      Set<FieldDescriptor> fields = createFields(cache, entity, g);
      if(fields.isEmpty()) throw new IllegalArgumentException(
            format("%s does not define any fields to marshall", entity.getSimpleName()));

      try {
         Constructor<?> constructor = entity.getDeclaredConstructor();
         constructor.setAccessible(true);
         return cache.define(g, new ConcreteEntityDescriptor(constructor, fields));
      } catch (SecurityException e) {
         throw new IllegalArgumentException(
               format("%s's constructor cannot be accessed.", entity.getSimpleName()));
      } catch (NoSuchMethodException e) {
         throw new IllegalArgumentException(
               format("%s does not have a no argument constructor.", entity.getSimpleName()));
      }
   }


   <T> Set<FieldDescriptor> createFields(Cache cache, Class<T> entity, Type g)
   {
      Set<FieldDescriptor> fields = new LinkedHashSet<>();
      if (entity.getSuperclass() != Object.class) {
         fields.addAll(createFields(cache, entity.getSuperclass(), Generics.resolveSuperTypes(g)));
      }

      Map<TypeVariable,Type> mappings = (g instanceof ParameterizedType) ?
            Generics.mapTypeVars((ParameterizedType)g) : null;

      for (Field field : entity.getDeclaredFields()) {
         Value value = field.getAnnotation(Value.class);
         if (value != null) {
            // Possibly need to translate fields with TypeVariables
            Type fieldType = field.getGenericType();
            if(fieldType instanceof TypeVariable && mappings != null) {
               fieldType = (mappings.containsKey(fieldType)) ? mappings.get(fieldType) : fieldType;
            }
            try {
               FieldDescriptor desc = new DirectFieldDescriptor(field, create(cache, fieldType));
               if (!fields.add(desc)) {
                  throw new IllegalArgumentException(
                        format("%s has a field collision on %s with one of its super classes",
                              entity.getSimpleName(), desc.getFieldName().getString()));

               }
            } catch(SecurityException e) {
               throw new IllegalArgumentException(
                     format("%s's %s field cannot be accessed.", entity.getSimpleName(), field.getName()));
            }
         }
      }
      return fields;
   }


   // Creates descriptors for java collections like Collection, List, Set, and Queue???
   @SuppressWarnings("unchecked")
   CollectionDescriptor createCollection(Cache cache, ParameterizedType pt)
   {
      Class<? extends Collection<?>> c = (Class<? extends Collection<?>>) pt.getRawType();
      Type[] types = pt.getActualTypeArguments();
      return new CollectionDescriptor(c, create(cache, types[0]));
   }


   // Creates descriptors for java maps like LinkedHashMap, TreeMap, etc
   @SuppressWarnings("unchecked")
   MapDescriptor createMap(Cache cache, ParameterizedType pt)
   {
      Class<? extends Map<?, ?>> c = (Class<? extends Map<?, ?>>) pt.getRawType();
      Type[] types = pt.getActualTypeArguments();
      if (!Generics.isType(pt, 0, String.class))
         throw new IllegalArgumentException("map keys must be strings");
      return new MapDescriptor(c, create(cache, types[1]));
   }


   ArrayDescriptor createArray(Cache cache, Type type)
   {
      if (type instanceof Class) {
         Class<?> c = (Class<?>) type;
         return new ArrayDescriptor(create(cache, (Type)c.getComponentType()));
      } else {
         GenericArrayType gat = (GenericArrayType) type;
         return new ArrayDescriptor(create(cache, gat.getGenericComponentType()));
      }
   }





   // External Entry point to create EntityDescriptors
   @SuppressWarnings("unchecked")
   public static <T> EntityDescriptor<T> create(Class<T> c)
   {
      DescriptorFactory factory = new DescriptorFactory();
      if (c.getTypeParameters().length > 0) {
         throw new IllegalArgumentException("top level entities must define all generics");
      }
      Cache cache = new Cache();
      return (EntityDescriptor<T>) factory.createEntity(cache, c);
   }






   private static class DelegatingEntityDescriptor<T> implements EntityDescriptor<T> {

      private EntityDescriptor<T> delegate;


      public void setDelegate(EntityDescriptor<T> delegate)
      {
         this.delegate = delegate;
      }

      @Override
      public String getDiscriminator()
      {
         return delegate.getDiscriminator();
      }

      @Override
      public Set<FieldDescriptor> getFieldDescriptors()
      {
         return delegate.getFieldDescriptors();
      }

      @Override
      public Class<?> getReturnedClass()
      {
         return delegate.getReturnedClass();
      }

      @Override
      public JSONObject marshall(T entity, String view)
      {
         return delegate.marshall(entity, view);
      }

      @Override
      public T unmarshall(JSONObject marshalled, String view)
      {
         return delegate.unmarshall(marshalled, view);
      }

      @Override
      public JSONValue marshall(FieldDescriptor fieldDescriptor, Object entity, String view)
      {
         return delegate.marshall(fieldDescriptor, entity, view);
      }

      @Override
      public void unmarshall(FieldDescriptor fieldDescriptor, Object entity, JSONValue marshalled, String view)
      {
         delegate.unmarshall(fieldDescriptor, entity, marshalled, view);
      }

      @Override
      public JSONObject marshallArray(Object array, int index, String view)
      {
         return delegate.marshallArray(array, index, view);
      }

      @Override
      public void unmarshallArray(Object array, JSONValue value, int index, String view)
      {
         delegate.unmarshallArray(array, value, index, view);
      }


      @Override
      public boolean equals(Object obj)
      {
         return delegate.equals(obj);
      }

      @Override
      public int hashCode()
      {
         return delegate.hashCode();
      }

      @Override
      public String toString(int pad)
      {
         return delegate.toString(pad);
      }
   }

   private static class Cache {

      private Map<Type,DelegatingEntityDescriptor<?>> cache = new HashMap<>();

      public boolean contains(Type type)
      {
         if(cache.containsKey(type)) return true;
         cache.put(type, new DelegatingEntityDescriptor());
         return false;
      }

      public EntityDescriptor<?> get(Type type)
      {
         return cache.get(type);
      }

      public EntityDescriptor define(Type type, EntityDescriptor delegate)
      {
         cache.get(type).setDelegate(delegate);
         return delegate;
      }

   }



   private static Type remap(final Class<?> subclass, final Type g)
   {
      if(g instanceof Class) {
         return subclass;
      } else if(g instanceof ParameterizedType) {
         final ParameterizedType pt = (ParameterizedType) g;
         return new ParameterizedType() {
            @Override
            public Type[] getActualTypeArguments()
            {
               return pt.getActualTypeArguments();
            }

            @Override
            public Type getRawType()
            {
               return subclass;
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
               builder.append(subclass.getName()).append("<");
               Type[] args = pt.getActualTypeArguments();
               for(int i = 0; i < args.length; i++) {
                  if(i > 0) builder.append(", ");
                  builder.append(args[i]);
               }
               return builder.append(">").toString();
            }
         };
      } else {
         return null;   // TODO Can we have a GenericArrayType here?? Anything else??
      }
   }


}
