/**
 * Created by IntelliJ IDEA.
 * User: cfloersch
 * Date: 4/11/11 3:01 PM
 * Copyright Manheim online
 */
package xpertss.json.roll;

import xpertss.json.Entity;
import xpertss.json.Value;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Rollcall {

   @Value
   private List<Person> people = new ArrayList<Person>();

   public void add(Person v)
   {
      people.add(v);
   }

   public boolean equals(Object o)
   {
      if(o instanceof Rollcall) {
         Rollcall rc = (Rollcall) o;
         return people.equals(rc.people);
      }
      return false;
   }


}
