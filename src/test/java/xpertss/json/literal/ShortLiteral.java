/**
 * Created By: cfloersch
 * Date: 6/14/2014
 * Copyright 2013 XpertSoftware
 */
package xpertss.json.literal;

import xpertss.json.Entity;
import xpertss.json.Value;

@Entity
public class ShortLiteral {

   @Value
   private short value;

   public short value() { return value; }

}
