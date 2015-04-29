package org.xpertss.json.types;

import xpertss.json.JSONString;
import xpertss.json.MarshallingException;
import xpertss.json.spi.JSONUserType;

import java.util.UUID;

import static xpertss.json.JSON.string;

public class UUIDType implements JSONUserType<UUID, JSONString> {

   @Override
   public JSONString marshall(UUID entity)
   {
      return string(entity.toString());
   }

   @Override
   public UUID unmarshall(JSONString object)
   {
      try {
         return UUID.fromString(object.getString());
      } catch(IllegalArgumentException e) {
         throw new MarshallingException("invalid uuid form", e);
      }
   }

   @Override
   public Class<UUID> getReturnedClass()
   {
      return UUID.class;
   }
}
