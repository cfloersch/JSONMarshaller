/**
 * Created By: cfloersch
 * Date: 6/6/2014
 * Copyright 2013 XpertSoftware
 */
package org.xpertss.json.types;

import xpertss.json.JSONString;
import xpertss.json.MarshallingException;
import xpertss.json.spi.JSONUserType;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import static xpertss.json.JSON.string;

public class DateType implements JSONUserType<Date, JSONString> {

   private static final String FORMAT = "yyyy-MM-dd";

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
         return new Date(createFormat().parse(object.getString()).getTime());
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
