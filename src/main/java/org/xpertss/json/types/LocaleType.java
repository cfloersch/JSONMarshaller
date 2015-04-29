package org.xpertss.json.types;

import xpertss.json.JSONString;
import xpertss.json.MarshallingException;
import xpertss.json.spi.JSONUserType;
import org.xpertss.json.util.Strings;

import java.util.Locale;

import static java.lang.String.format;
import static xpertss.json.JSON.string;

/**
 * Created by cfloersch on 6/17/2014.
 */
public class LocaleType implements JSONUserType<Locale, JSONString> {

   public JSONString marshall(Locale entity)
   {
      return string(entity.getLanguage().toLowerCase() + "-" + entity.getCountry().toUpperCase());
   }

   public Locale unmarshall(JSONString object)
   {
      String str = object.getString();
      if (!Strings.isEmpty(str)) {
         String[] parts = str.split("[-_]");
         if(parts.length == 1) {
            return new Locale(parts[0].toLowerCase());
         } else {
            return new Locale(parts[0].toLowerCase(), parts[1].toUpperCase());
         }
      }
      throw new MarshallingException(format("locale %s invalid", str));
   }

   public Class<Locale> getReturnedClass() { return Locale.class; }
}
