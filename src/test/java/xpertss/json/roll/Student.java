/**
 * Created by IntelliJ IDEA.
 * User: cfloersch
 * Date: 4/11/11 2:57 PM
 * Copyright Manheim online
 */
package xpertss.json.roll;

import xpertss.json.Entity;
import xpertss.json.Value;

import java.util.Objects;

@Entity(discriminator="Student")
public class Student extends Person {

   @Value
   private Grade grade;

   public Student() { }
   public Student(String name, String email, Grade grade)
   {
      super(name, email);
      this.grade = grade;
   }

   public boolean equals(Object obj)
   {
      if(obj instanceof Student) {
         Student o = (Student) obj;
         return super.equals(o) &&
                 Objects.equals(grade, o.grade);
      }
      return false;
   }

   public int hashCode()
   {
      return Objects.hash(getName(), getEmail(), grade);
   }

}
