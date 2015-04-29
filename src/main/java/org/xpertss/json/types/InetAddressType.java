/**
 * Created By: cfloersch
 * Date: 6/7/2014
 * Copyright 2013 XpertSoftware
 */
package org.xpertss.json.types;

import xpertss.json.JSONString;
import xpertss.json.MarshallingException;
import xpertss.json.spi.JSONUserType;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static xpertss.json.JSON.string;

public class InetAddressType implements JSONUserType<InetAddress, JSONString> {

   public JSONString marshall(InetAddress entity) { return string(entity.getHostAddress()); }

   public InetAddress unmarshall(JSONString object)
   {
      try {
         return InetAddress.getByName(object.getString());
      } catch(UnknownHostException e) {
         throw new MarshallingException("unknown host for inet address", e);
      }
   }

   public Class<InetAddress> getReturnedClass()
   {
      return InetAddress.class;
   }

}
