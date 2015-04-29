package xpertss.json.garage;


import xpertss.json.Entity;
import xpertss.json.Value;

import java.util.Objects;

@Entity
public class House {

   @Value
   private Vehicle vehicle;

   public House() { }
   public House(Vehicle vehicle) { this.vehicle = vehicle; }

   public boolean equals(Object obj)
   {
      if(obj instanceof House) {
         House o = (House) obj;
         return Objects.equals(vehicle, o.vehicle);
      }
      return false;
   }

   public int hashCode()
   {
      return Objects.hash(vehicle);
   }

}
