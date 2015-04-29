/**
 * Created By: cfloersch
 * Date: 6/7/2014
 * Copyright 2013 XpertSoftware
 */
package org.xpertss.json.desc.entity;

import xpertss.json.Value;

public class BaseClass {

   @Value
   private String name;


   protected BaseClass() { }

   public String getName()
   {
      return name;
   }

}
