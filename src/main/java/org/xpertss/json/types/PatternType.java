package org.xpertss.json.types;

import xpertss.json.*;
import xpertss.json.spi.JSONUserType;

import java.util.regex.Pattern;

import static xpertss.json.JSON.string;

public class PatternType implements JSONUserType<Pattern, JSONObject> {

   private static final JSONString PATTERN = string("pattern");
   private static final JSONString FLAGS = string("flags");

   @Override
   public JSONObject marshall(Pattern entity)
   {
      JSONObjectBuilder builder = new JSONObjectBuilder();
      builder.add(FLAGS.getString(), entity.flags());
      builder.add(PATTERN.getString(), entity.pattern());
      return builder.build();
   }

   @Override
   public Pattern unmarshall(JSONObject object)
   {
      try {
         String pattern = getString(object, PATTERN);
         if(!object.containsKey(FLAGS)) {
            return Pattern.compile(pattern);
         }
         return Pattern.compile(pattern, getInt(object, FLAGS));
      } catch(RuntimeException e) {
         throw new MarshallingException(e);
      }
   }

   @Override
   public Class<Pattern> getReturnedClass()
   {
      return Pattern.class;
   }


   private static int getInt(JSONObject obj, JSONString key)
   {
      JSONNumber number = (JSONNumber) obj.get(key);
      return number.getNumber().intValueExact();
   }

   private static String getString(JSONObject obj, JSONString key)
   {
      JSONString string = (JSONString) obj.get(key);
      return string.getString();
   }

}
