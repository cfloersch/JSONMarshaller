package org.xpertss.json.desc;

import org.junit.Test;
import xpertss.json.JSONString;

import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;

import static org.junit.Assert.*;

public class UserTypeDescriptorTest {


   private DescriptorFactory factory = new DescriptorFactory();


   @Test
   public void testUri()
   {
      URI testObj = URI.create("http://www.google.com/");
      UserTypeDescriptor<URI,JSONString> desc = (UserTypeDescriptor<URI,JSONString>) factory.create((Type)testObj.getClass());
      JSONString value = desc.marshall(testObj, null);
      assertEquals(testObj, desc.unmarshall(value, null));
   }

   @Test
   public void testUrl() throws Exception
   {
      URL testObj = new URL("http://www.google.com/");
      UserTypeDescriptor<URL,JSONString> desc = (UserTypeDescriptor<URL,JSONString>) factory.create((Type)testObj.getClass());
      JSONString value = desc.marshall(testObj, null);
      assertEquals(testObj, desc.unmarshall(value, null));
   }

   @Test
   public void testDate() throws Exception
   {
      Timestamp testObj = new Timestamp(System.currentTimeMillis());
      UserTypeDescriptor<Timestamp,JSONString> desc = (UserTypeDescriptor<Timestamp,JSONString>) factory.create((Type)testObj.getClass());
      JSONString value = desc.marshall(testObj, null);
      assertEquals(testObj, desc.unmarshall(value, null));
   }

   @Test
   public void testInetAddress() throws Exception
   {
      InetAddress testObj = InetAddress.getByName("10.10.1.1");
      UserTypeDescriptor<InetAddress,JSONString> desc = (UserTypeDescriptor<InetAddress,JSONString>) factory.create((Type)InetAddress.class);
      JSONString value = desc.marshall(testObj, null);
      assertEquals(testObj, desc.unmarshall(value, null));
   }


}