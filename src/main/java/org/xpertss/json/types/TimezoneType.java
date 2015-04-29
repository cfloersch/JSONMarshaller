package org.xpertss.json.types;

import xpertss.json.JSONString;
import xpertss.json.spi.JSONUserType;

import java.util.TimeZone;

import static xpertss.json.JSON.string;

/**
 * Created by cfloersch on 6/17/2014.
 */
public class TimezoneType implements JSONUserType<TimeZone, JSONString> {

   public JSONString marshall(TimeZone entity)
   {
      return string(entity.getID());
   }

   // Unfortunately it always unmarshalls to GMT when input is gibberish
   public TimeZone unmarshall(JSONString object)
   {
      return TimeZone.getTimeZone(object.getString());
   }

   public Class<TimeZone> getReturnedClass() { return TimeZone.class; }
}
