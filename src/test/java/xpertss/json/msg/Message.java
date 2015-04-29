package xpertss.json.msg;


import xpertss.json.Entity;
import xpertss.json.Value;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Entity(discriminatorName = "ident", subclasses = {LoginMessage.class, LogoffMessage.class, BidderRegMessage.class})
public abstract class Message {

   @Value
   private Message[] attachments;


   public abstract String ident();


   public <T extends Message> T[] getAttachments(Class<T> cls)
   {
      List<T> results = new ArrayList<>();
      for(Message attachment : attachments) {
         if(cls.isAssignableFrom(attachment.getClass())) {
            results.add(cls.cast(attachment));
         }
      }
      return results.toArray((T[]) Array.newInstance(cls, results.size()));
   }

   public <T extends Message> void addAttachment(T msg)
   {
      if(msg != null) {
         if (attachments == null) {
            attachments = new Message[1];
         } else {
            Message[] newAttach = new Message[attachments.length + 1];
            System.arraycopy(attachments, 0, newAttach, 0, attachments.length);
            attachments = newAttach;
         }
         attachments[attachments.length - 1] = msg;
      }
   }

   public int getAttachmentCount()
   {
      return (attachments == null) ? 0 : attachments.length;
   }
}
