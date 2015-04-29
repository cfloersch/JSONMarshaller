package org.xpertss.json.desc;

import xpertss.json.JSONObject;
import xpertss.json.JSONString;

public interface FieldDescriptor {



   void marshall(Object entity, String view, JSONObject jsonObject);

   void unmarshall(Object entity, String view, JSONObject jsonObject);





   /**
    * Gets the field's Java name.
    */
   JSONString getFieldName();




   /**
    * Gets the described field's value.
    */
   Object getFieldValue(Object entity);

   byte getFieldValueByte(Object entity);

   char getFieldValueChar(Object entity);

   boolean getFieldValueBoolean(Object entity);

   short getFieldValueShort(Object entity);

   int getFieldValueInt(Object entity);

   long getFieldValueLong(Object entity);

   float getFieldValueFloat(Object entity);

   double getFieldValueDouble(Object entity);


   /**
    * Sets the described field's value.
    */
   void setFieldValue(Object entity, Object value);

   void setFieldValueByte(Object entity, byte value);

   void setFieldValueChar(Object entity, char value);

   void setFieldValueBoolean(Object entity, boolean value);

   void setFieldValueShort(Object entity, short value);

   void setFieldValueInt(Object entity, int value);

   void setFieldValueLong(Object entity, long value);

   void setFieldValueFloat(Object entity, float value);

   void setFieldValueDouble(Object entity, double value);




   /**
    * Tests whether the field is in a specific view.
    */
   boolean isInView(String view);


   /**
    * Tests whether the field is optional.
    */
   boolean isOptional();


   /**
    * Pretty prints the descriptor.
    */
   String toString(int pad);

}
