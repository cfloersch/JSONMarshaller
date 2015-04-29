package xpertss.json.msg;

import xpertss.json.Entity;
import xpertss.json.Value;


@Entity(discriminator = "logoff")
public class LogoffMessage extends Message {

   @Value
   private String username;

   public LogoffMessage() { }
   public LogoffMessage(String username) { this.username = username; }


   @Override
   public String ident()
   {
      return "logoff";
   }

   public String getUsername()
   {
      return username;
   }


}
