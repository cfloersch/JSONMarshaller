/**
 * Created by IntelliJ IDEA.
 * User: cfloersch
 * Date: 4/11/11 2:56 PM
 * Copyright Manheim online
 */
package xpertss.json.roll;

import xpertss.json.Entity;
import xpertss.json.Value;

import java.util.Objects;

@Entity(discriminatorName ="person", subclasses={Teacher.class, Student.class})
public abstract class Person {

   @Value
   private String name;

   @Value
   private String email;

   public Person() { }
   public Person(String name, String email)
   {
      this.email = email;
      this.name = name;
   }


   public String getName()
    {
        return name;
    }

   public String getEmail()
    {
        return email;
    }

   public boolean equals(Object o)
   {
      if(o instanceof Person) {
         Person p = (Person) o;
         return Objects.equals(name, p.name) &&
                 Objects.equals(email, p.email);
      }
      return false;
   }

   public int hashCode()
   {
       return Objects.hash(name, email);
   }
}
