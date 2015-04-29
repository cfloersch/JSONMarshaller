/**
 * Created By: cfloersch
 * Date: 6/14/2014
 * Copyright 2013 XpertSoftware
 */
package xpertss.json.object;

import xpertss.json.Entity;
import xpertss.json.Value;

@Entity
public class LongObject {

   @Value
   private Long value;

   public LongObject() { }
   public LongObject(Long value) { this.value = value; }

   public Long value() { return value; }

}
