package org.xpertss.json.desc;

import org.junit.Test;

import java.lang.reflect.Field;
import java.net.URI;
import java.net.URL;
import java.util.Date;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by cfloersch on 6/12/2014.
 */
public class UserTypesTest {

   private DescriptorFactory factory = new DescriptorFactory();


   // User Types Tests
   private URI uri;
   private URL url;
   private Date date;

   @Test
   public void testUserTypeUri() throws Exception
   {
      Field field = getClass().getDeclaredField("uri");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("UserTypeDescriptor<URI>", desc.toString());
   }

   @Test
   public void testUserTypeUrl() throws Exception
   {
      Field field = getClass().getDeclaredField("url");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("UserTypeDescriptor<URL>", desc.toString());
   }

   @Test
   public void testUserTypeDate() throws Exception
   {
      Field field = getClass().getDeclaredField("date");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("UserTypeDescriptor<Date>", desc.toString());
   }

   private Set<URI> uriSet;

   @Test
   public void testSetOfUris() throws Exception
   {
      Field field = getClass().getDeclaredField("uriSet");
      Descriptor desc = factory.create(field.getGenericType());
      assertEquals("Set<UserTypeDescriptor<URI>>", desc.toString());
   }

}
