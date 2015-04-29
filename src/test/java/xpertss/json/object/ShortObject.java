/**
 * Created By: cfloersch
 * Date: 6/14/2014
 * Copyright 2013 XpertSoftware
 */
package xpertss.json.object;

import xpertss.json.Entity;
import xpertss.json.Value;

@Entity
public class ShortObject {

   @Value
   private Short value;

   public Short value() { return value; }

}
