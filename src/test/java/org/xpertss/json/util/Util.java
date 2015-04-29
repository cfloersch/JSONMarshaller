/**
 * Created by IntelliJ IDEA.
 * User: cfloersch
 * Date: 4/8/11 3:02 PM
 * Copyright Manheim online
 */
package org.xpertss.json.util;

public class Util {


   public static boolean equals(Object o1, Object o2)
   {
      return (o1 == null) ? o2 == null : o1.equals(o2);
   }

   public static int hashCode(Object ... objs)
   {
      int code = 0;
      for(Object o : objs) {
         code ^= System.identityHashCode(o);
      }
      return code;
   }

}
