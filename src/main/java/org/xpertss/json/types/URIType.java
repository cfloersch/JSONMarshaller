package org.xpertss.json.types;

import xpertss.json.JSONString;
import xpertss.json.MarshallingException;
import xpertss.json.spi.JSONUserType;

import java.net.URI;
import java.net.URISyntaxException;

import static xpertss.json.JSON.string;

public class URIType implements JSONUserType<URI, JSONString> {

   public JSONString marshall(URI entity)
   {
      return string(entity.toString());
   }

   public URI unmarshall(JSONString object)
   {
      try {
         return new URI(object.getString());
      } catch(URISyntaxException e) {
         throw new MarshallingException("invalid uri syntax", e);
      }
   }

   public Class<URI> getReturnedClass()
   {
      return URI.class;
   }
}
