/**
 * Created by IntelliJ IDEA.
 * User: cfloersch
 * Date: 4/11/11 12:53 PM
 * Copyright Manheim online
 */
package xpertss.json.garage;

import xpertss.json.Entity;
import xpertss.json.Value;

@Entity(discriminator = "Motorcycle")
public class Motorcycle implements Vehicle {

   @Value
   private int wheels = 2;

   public int wheelCount() {
      return wheels;
   }

   public boolean equals(Object o)
   {
      return (o instanceof Motorcycle);
   }

}
