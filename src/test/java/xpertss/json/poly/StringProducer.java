/**
 * Created By: cfloersch
 * Date: 6/13/2014
 * Copyright 2013 XpertSoftware
 */
package xpertss.json.poly;

import xpertss.json.Entity;
import xpertss.json.Value;

import java.util.Objects;

@Entity(discriminator = "string")
public class StringProducer implements Producer<String> {

   @Value
   private String item;

   public StringProducer() { }
   public StringProducer(String item) { this.item = item; }


   @Override
   public String produce()
   {
      return item;
   }


   public boolean equals(Object obj)
   {
      if(obj instanceof StringProducer) {
         StringProducer o = (StringProducer) obj;
         return Objects.equals(item, o.item);
      }
      return false;
   }

   public int hashCode()
   {
      return Objects.hashCode(item);
   }

}
