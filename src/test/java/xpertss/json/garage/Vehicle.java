/**
 * Created by IntelliJ IDEA.
 * User: cfloersch
 * Date: 4/11/11 12:53 PM
 * Copyright Manheim online
 */
package xpertss.json.garage;

import xpertss.json.Entity;

@Entity(discriminatorName = "vehicletype", subclasses = {Car.class, Motorcycle.class})
public interface Vehicle {
   public int wheelCount();
}
