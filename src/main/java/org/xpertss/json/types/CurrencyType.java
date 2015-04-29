package org.xpertss.json.types;

import xpertss.json.JSONString;
import xpertss.json.MarshallingException;
import xpertss.json.spi.JSONUserType;

import java.util.Currency;

import static xpertss.json.JSON.string;

public class CurrencyType implements JSONUserType<Currency, JSONString> {

   @Override
   public JSONString marshall(Currency entity)
   {
      return string(entity.getCurrencyCode());
   }

   @Override
   public Currency unmarshall(JSONString object)
   {
      try {
         return Currency.getInstance(object.getString());
      } catch(IllegalArgumentException e) {
         throw new MarshallingException("unknown currency code: " + object.getString(), e);
      }
   }

   @Override
   public Class<Currency> getReturnedClass()
   {
      return Currency.class;
   }
}
