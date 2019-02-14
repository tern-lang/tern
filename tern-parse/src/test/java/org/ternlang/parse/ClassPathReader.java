package org.ternlang.parse;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class ClassPathReader {
   public static String load(String file) throws Exception {
      InputStream in = ClassPathReader.class.getResourceAsStream(file);

      if (in != null) {
         ByteArrayOutputStream out = new ByteArrayOutputStream();
         byte[] buffer = new byte[1024];
         int count = 0;
         while ((count = in.read(buffer)) != -1) {
            out.write(buffer, 0, count);
         }
         return out.toString();
      }
      return null;
   }
}
