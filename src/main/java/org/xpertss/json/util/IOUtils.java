/**
 * Created By: cfloersch
 * Date: 6/6/2014
 * Copyright 2013 XpertSoftware
 */
package org.xpertss.json.util;

import java.io.IOException;
import java.io.Reader;

public class IOUtils {

   public static int skip(Reader reader) throws IOException
   {
      int c;
      while(Character.isWhitespace(c = reader.read())) ;
      return c;
   }


}
