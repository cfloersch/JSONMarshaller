package org.xpertss.json.desc;

import xpertss.json.JSONString;

import static xpertss.json.JSON.NULL;
import static xpertss.json.JSON.string;

/**
 * Descriptor for the {@link String} type.
 */
public class StringDescriptor extends AbstractDescriptor<String, JSONString> {

   public final static StringDescriptor STRING_DESC = new StringDescriptor();

   private StringDescriptor()
   {
      super(String.class);
   }


   public final JSONString marshall(String entity, String view)
   {
      return entity == null ? NULL : string(entity);
   }

   public final String unmarshall(JSONString marshalled, String view)
   {
      return NULL.equals(marshalled) ? null : marshalled.getString();
   }



}
