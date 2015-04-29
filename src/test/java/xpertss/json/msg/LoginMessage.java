package xpertss.json.msg;

import xpertss.json.Entity;
import xpertss.json.Value;


@Entity(discriminator = "login")
public class LoginMessage extends Message {

   @Value
   private String username;

   @Value
   private UserType type;

   public LoginMessage() { }
   public LoginMessage(String username, UserType type) { this.username = username; this.type = type; }


   @Override
   public String ident()
   {
      return "login";
   }

   public String getUsername()
   {
      return username;
   }

   public UserType getUserType()
   {
      return type;
   }

}
