/**
 * Created By: cfloersch
 * Date: 6/7/2014
 * Copyright 2013 XpertSoftware
 */
package org.xpertss.json.desc.entity;

import xpertss.json.Entity;
import xpertss.json.Value;

@Entity
public class SubClass extends BaseClass {

   @Value
   private int age;

   public SubClass()
   {
      super();
   }

   public int getAge()
   {
      return age;
   }

}
