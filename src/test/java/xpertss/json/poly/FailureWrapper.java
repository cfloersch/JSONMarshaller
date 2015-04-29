/**
 * Created By: cfloersch
 * Date: 6/13/2014
 * Copyright 2013 XpertSoftware
 */
package xpertss.json.poly;

import xpertss.json.Entity;
import xpertss.json.Value;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class FailureWrapper {


   @Value
   private Set<Producer<Integer>> producers = new LinkedHashSet<>();



   public Set<Producer<Integer>> getProducers()
   {
      return producers;
   }

   public void addProducer(Producer<Integer> producer)
   {
      producers.add(producer);
   }


   public boolean equals(Object obj)
   {
      if(obj instanceof FailureWrapper) {
         FailureWrapper o = (FailureWrapper) obj;
         return Objects.equals(producers, o.producers);
      }
      return false;
   }

   public int hashCode()
   {
      return Objects.hashCode(producers);
   }


}
