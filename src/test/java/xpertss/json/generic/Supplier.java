package xpertss.json.generic;

import xpertss.json.Entity;
import xpertss.json.Value;

import java.util.Objects;

@Entity
public class Supplier<T> {


   @Value
   private T item;

   public Supplier() { }
   public Supplier(T item) { this.item = item; }

   public T get() { return item; }


   public boolean equals(Object obj)
   {
      if(obj instanceof Supplier) {
         Supplier o = (Supplier) obj;
         return Objects.equals(item, o.item);
      }
      return false;
   }

   public int hashCode()
   {
      return Objects.hashCode(item);
   }

}
