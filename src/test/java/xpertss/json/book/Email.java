/**
 * Created by IntelliJ IDEA.
 * User: cfloersch
 * Date: 4/11/11 2:12 PM
 * Copyright Manheim online
 */
package xpertss.json.book;

import xpertss.json.Entity;
import xpertss.json.Value;

import java.util.Objects;

@Entity
public class Email {

   public enum Type {
      Work, Home
   }

   @Value
   private String address;

   @Value
   private Type type;


   public Email() { }
   public Email(String address, Type type)
   {
      this.address = address;
      this.type = type;
   }

   public String getAddress() { return address; }
   public Type getType() { return type; }

   public boolean equals(Object o)
   {
      if(o instanceof Email) {
         Email e = (Email) o;
         return Objects.equals(address, e.address) &&
                 Objects.equals(type, e.type);
      }
      return false;
   }

    public int hashCode()
    {
        return Objects.hash(address, type);
    }

}
