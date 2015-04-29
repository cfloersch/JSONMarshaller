/**
 * Created by IntelliJ IDEA.
 * User: cfloersch
 * Date: 4/11/11 12:52 PM
 * Copyright Manheim online
 */
package xpertss.json.garage;

import xpertss.json.Entity;
import xpertss.json.Value;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Garage {

   @Value
   private List<Vehicle> vehicles = new ArrayList<Vehicle>();

   public void add(Vehicle v)
   {
      vehicles.add(v);
   }

   public boolean equals(Object o)
   {
      if(o instanceof Garage) {
         Garage g = (Garage) o;
         return vehicles.equals(g.vehicles);
      }
      return false;
   }
}
