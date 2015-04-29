/**
 * Created By: cfloersch
 * Date: 6/13/2014
 * Copyright 2013 XpertSoftware
 */
package xpertss.json.poly;

import xpertss.json.Entity;
import xpertss.json.Value;

import java.util.Objects;

@Entity(discriminator = "generic")
public class GenericProducer<T> implements Producer<T> {


   @Value
   private T item;

   public GenericProducer() { }
   public GenericProducer(T item) { this.item = item; }


   @Override
   public T produce()
   {
      return item;
   }


   public boolean equals(Object obj)
   {
      if(obj instanceof GenericProducer) {
         GenericProducer o = (GenericProducer) obj;
         return Objects.equals(item, o.item);
      }
      return false;
   }

   public int hashCode()
   {
      return Objects.hashCode(item);
   }

}
