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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static xpertss.json.JSON.string;

public class DateTimeType implements JSONUserType<Date, JSONString> {

   private static final String FORMAT = "yyyy-MM-dd'T'HH:mm:ssX";

   private static final ThreadLocal<DateFormat> cache = new ThreadLocal<DateFormat>() {
      protected DateFormat initialValue()
      {
         DateFormat format = new SimpleDateFormat(FORMAT);
         format.setTimeZone(TimeZone.getTimeZone("UTC"));
         return format;
      }
   };


   public JSONString marshall(Date entity)
   {
      return string(createFormat().format(entity));
   }

   public Date unmarshall(JSONString object)
   {
      try {
         return createFormat().parse(object.getString());
      } catch(ParseException e) {
         throw new MarshallingException("invalid date format", e);
      }
   }

   public Class<Date> getReturnedClass()
   {
      return Date.class;
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
