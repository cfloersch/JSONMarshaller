/**
 * Created by IntelliJ IDEA.
 * User: cfloersch
 * Date: 4/11/11 2:59 PM
 * Copyright Manheim online
 */
package xpertss.json.roll;

import xpertss.json.Entity;
import xpertss.json.Value;

import java.util.Objects;

@Entity(discriminator="Teacher")
public class Teacher extends Person {

   @Value
   private int years;

   public Teacher() { }
   public Teacher(String name, String email, int years)
   {
      super(name, email);
      this.years = years;
   }


   public boolean equals(Object obj)
   {
      if(obj instanceof Teacher) {
         Teacher o = (Teacher) obj;
         return super.equals(o) &&
                 Objects.equals(years, o.years);
      }
      return false;
   }

   public int hashCode()
   {
      return Objects.hash(getName(), getEmail(), years);
   }

}
