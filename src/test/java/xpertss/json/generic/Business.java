package xpertss.json.generic;

import xpertss.json.Entity;
import xpertss.json.Value;
import xpertss.json.book.Book;

import java.util.Objects;

@Entity
public class Business {

   @Value
   private Supplier<Book> supplier;

   @Value
   private String name;

   public Business() { }
   public Business(String name, Book item)
   {
      this.name = name;
      this.supplier = new Supplier<>(item);
   }


   public boolean equals(Object obj)
   {
      if(obj instanceof Business) {
         Business o = (Business) obj;
         return Objects.equals(supplier, o.supplier) &&
                 Objects.equals(name, o.name);
      }
      return false;
   }

   public int hashCode()
   {
      return Objects.hash(supplier, name);
   }

}
