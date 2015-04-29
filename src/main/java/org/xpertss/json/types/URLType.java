package org.xpertss.json.types;

import xpertss.json.JSONString;
import xpertss.json.MarshallingException;
import xpertss.json.spi.JSONUserType;

import java.net.MalformedURLException;
import java.net.URL;

import static xpertss.json.JSON.string;

/**
 * A type converter for {@link URL} objects.
 */
public class URLType implements JSONUserType<URL, JSONString> {

   public JSONString marshall(URL entity)
   {
      return string(entity.toString());
   }

   public URL unmarshall(JSONString object)
   {
      try {
         return new URL(object.getString());
      } catch(MalformedURLException e) {
         throw new MarshallingException("invalid url format", e);
      }
   }

   public Class<URL> getReturnedClass()
   {
      return URL.class;
   }
}
