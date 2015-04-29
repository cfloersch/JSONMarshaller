/**
 * Created By: cfloersch
 * Date: 6/7/2014
 * Copyright 2013 XpertSoftware
 */
package org.xpertss.json.desc.entity;

import xpertss.json.Entity;
import xpertss.json.Value;

@Entity
public class NonBean {

   @Value
   private String name;

   public NonBean(String name)
   {
      this.name = name;
   }


   public String getName()
   {
      return name;
   }
}
