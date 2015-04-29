/**
 * Created By: cfloersch
 * Date: 6/19/2014
 * Copyright 2013 XpertSoftware
 */
package xpertss.json.msg;

import xpertss.json.Entity;
import xpertss.json.Value;


@Entity(discriminator = "bidreg")
public class BidderRegMessage extends Message {

   @Value
   private long account;

   @Value
   private int bidderno;


   public BidderRegMessage() { }
   public BidderRegMessage(long account, int bidderno) { this.account = account; this.bidderno = bidderno; }


   @Override
   public String ident()
   {
      return "bidreg";
   }

   public long getAccount()
   {
      return account;
   }

   public int getBidderNumber()
   {
      return bidderno;
   }

}

