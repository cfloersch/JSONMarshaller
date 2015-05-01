/**
 * Copyright 2015 XpertSoftware
 * <p/>
 * Created By: cfloersch
 * Date: 5/1/2015
 */
package org.xpertss.json.types;

import xpertss.json.JSONString;
import xpertss.json.MarshallingException;
import xpertss.json.spi.JSONUserType;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import static xpertss.json.JSON.string;

public class TimeType implements JSONUserType<Time, JSONString> {

   private DateFormat format = createFormat();


   public JSONString marshall(Time entity)
   {
      return string(format.format(entity));
   }

   public Time unmarshall(JSONString object)
   {
      try {

         return new Time(format.parse(object.getString()).getTime());
      } catch(ParseException e) {
         throw new MarshallingException("invalid date format", e);
      }
   }

   public Class<Time> getReturnedClass()
   {
      return Time.class;
   }


   private static DateFormat createFormat()
   {
      DateFormat format = new SimpleDateFormat("HH:mm:ss.SSSX");
      format.setTimeZone(TimeZone.getTimeZone("UTC"));
      return format;
   }


}
