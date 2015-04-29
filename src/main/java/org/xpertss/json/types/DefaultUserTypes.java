/**
 * Created By: cfloersch
 * Date: 6/6/2014
 * Copyright 2013 XpertSoftware
 */
package org.xpertss.json.types;

import xpertss.json.JSONValue;
import xpertss.json.spi.JSONUserType;
import xpertss.json.spi.UserTypeService;

import javax.activation.MimeType;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Pattern;

public class DefaultUserTypes implements UserTypeService {

   public JSONUserType<?, ? extends JSONValue> create(Type type)
   {
      if(type instanceof Class) {
         Class klass = (Class) type;
         if (klass == URI.class) {
            return new URIType();
         } else if (klass == URL.class) {
            return new URLType();
         } else if (klass == Date.class) {
            return new DateType();
         } else if (klass == InetAddress.class) {
            return new InetAddressType();
         } else if (klass == MimeType.class) {
            return new MimeTypeType();
         } else if (klass == Locale.class) {
            return new LocaleType();
         } else if (klass == TimeZone.class) {
            return new TimezoneType();
         } else if (klass == Currency.class) {
            return new CurrencyType();
         } else if (klass == UUID.class) {
            return new UUIDType();
         } else if (klass == Pattern.class) {
            return new PatternType();
         }
      }
      return null;
   }
}
