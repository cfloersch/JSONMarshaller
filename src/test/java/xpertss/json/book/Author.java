/**
 * Created by IntelliJ IDEA.
 * User: cfloersch
 * Date: 4/8/11 2:27 PM
 * Copyright Manheim online
 */
package xpertss.json.book;

import xpertss.json.Entity;
import org.xpertss.json.util.Util;
import xpertss.json.Value;

@Entity
public class Author {

   @Value
   private String firstName;
   @Value
   private String lastName;

   public Author() { }
   public Author(String fname, String lname)
   {
      this.firstName = fname;
      this.lastName = lname;
   }

   public String getFirstName()
   {
      return firstName;
   }

   public void setFirstName(String firstName)
   {
      this.firstName = firstName;
   }

   public String getLastName()
   {
      return lastName;
   }

   public void setLastName(String lastName)
   {
      this.lastName = lastName;
   }

   public boolean equals(Object o)
   {
      if(o instanceof Author) {
         Author a = (Author) o;
         return Util.equals(firstName, a.firstName) && Util.equals(lastName, a.lastName);
      }
      return false;
   }

   public int hashCode()
   {
      return Util.hashCode(firstName, lastName);
   }
}
