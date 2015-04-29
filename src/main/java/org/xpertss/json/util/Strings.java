/**
 * Created by IntelliJ IDEA.
 * User: cfloersch
 * Date: 4/13/11 1:53 PM
 * Copyright Manheim online
 */
package org.xpertss.json.util;

public class Strings {

   public static boolean isEmpty(String str)
   {
      return (str == null || str.trim().length() < 1);
   }

   public static String ifEmpty(String str, String def)
   {
      return (isEmpty(str)) ? def : str;
   }


}
