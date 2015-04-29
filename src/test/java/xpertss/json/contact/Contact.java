/**
 * Created By: cfloersch
 * Date: 6/15/2014
 * Copyright 2013 XpertSoftware
 */
package xpertss.json.contact;

import xpertss.json.Entity;
import xpertss.json.Value;

import java.util.Objects;

@Entity
public class Contact {

   @Value
   private String name;

   @Value(optional = true)
   private String email;



   public Contact() { }
   public Contact(String name, String email) { this.name = name; this.email = email; }

   public String name() { return name; }
   public String email() { return email; }


   public boolean equals(Object obj)
   {
      if(obj instanceof Contact) {
         Contact o = (Contact) obj;
         return Objects.equals(name, o.name) && Objects.equals(email, o.email);
      }
      return false;
   }

   public int hashCode()
   {
      return Objects.hash(name, email);
   }


}
