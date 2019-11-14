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

   private static final String FORMAT = "HH:mm:ssX";

   private static final ThreadLocal<DateFormat> cache = new ThreadLocal<DateFormat>() {
      protected DateFormat initialValue()
      {
         DateFormat format = new SimpleDateFormat(FORMAT);
         format.setTimeZone(TimeZone.getTimeZone("UTC"));
         return format;
      }
   };

   public JSONString marshall(Time entity)
   {
      return string(createFormat().format(entity));
   }

   public Time unmarshall(JSONString object)
   {
      try {

         return new Time(createFormat().parse(object.getString()).getTime());
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
      if("false".equals(System.getProperty("xpertss.date.types.cache", "true"))) {
         DateFormat format = new SimpleDateFormat(FORMAT);
         format.setTimeZone(TimeZone.getTimeZone("UTC"));
         return format;
      }
      return cache.get();
   }


}
