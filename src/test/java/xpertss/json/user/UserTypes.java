/**
 * Created By: cfloersch
 * Date: 2/5/2015
 * Copyright 2013 XpertSoftware
 */
package xpertss.json.user;

import xpertss.json.Entity;
import xpertss.json.Value;

import java.util.Currency;
import java.util.UUID;
import java.util.regex.Pattern;

@Entity
public class UserTypes {

   @Value
   private Pattern pattern;

   @Value
   private UUID uuid;

   @Value
   private Currency currency;


   public UserTypes() { }
   public UserTypes(Pattern pattern, UUID uuid, Currency currency)
   {
      this.pattern = pattern;
      this.uuid = uuid;
      this.currency = currency;
   }

   public Pattern getPattern()
   {
      return pattern;
   }

   public UUID getUuid()
   {
      return uuid;
   }

   public Currency getCurrency()
   {
      return currency;
   }


}
