/**
 * Created By: cfloersch
 * Date: 6/14/2014
 * Copyright 2013 XpertSoftware
 */
package xpertss.json.object;

import xpertss.json.Entity;
import xpertss.json.Value;

@Entity
public class BooleanObject {

   @Value
   private Boolean value;


   public Boolean value() { return value; }
}
