package org.xpertss.json.types;

import xpertss.json.JSONString;
import xpertss.json.MarshallingException;
import xpertss.json.spi.JSONUserType;

import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;

import static xpertss.json.JSON.string;

/**
 * Created by cfloersch on 6/17/2014.
 */
public class MimeTypeType implements JSONUserType<MimeType, JSONString> {

   public JSONString marshall(MimeType entity)
   {
      return string(entity.toString());
   }

   public MimeType unmarshall(JSONString object)
   {
      try {
         return new MimeType(object.getString());
      } catch (MimeTypeParseException e) {
         throw new MarshallingException("invalid mime type syntax", e);
      }
   }

   public Class<MimeType> getReturnedClass()
   {
      return MimeType.class;
   }
}
