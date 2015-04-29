/**
 * Created By: cfloersch
 * Date: 6/15/2014
 * Copyright 2013 XpertSoftware
 */
package xpertss.json.object;

import xpertss.json.Entity;
import xpertss.json.Value;

@Entity
public class StringObject {

   @Value
   private String value;

   public String value() { return value; }

}
