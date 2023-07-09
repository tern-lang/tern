package org.ternlang.compile;

public class JvmVersion {
   private static final String VERSION = "java.specification.version";

   public static String getSpecVersion() {
      return System.getProperty(VERSION);
   }

   public static int getVersion() {
      String version = getSpecVersion();
      int index = version.indexOf(".");

      if(index != -1) {
         return Integer.parseInt(version.substring(index));
      }
      return Integer.parseInt(version);
   }
}
