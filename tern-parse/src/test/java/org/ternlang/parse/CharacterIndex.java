package org.ternlang.parse;

public class CharacterIndex {

   public static void main(String[] list) throws Exception {
      System.err.println("private static final short[] IDENTIFIER = {\n");
      for(int i = 0; i < 255; i++) {
         if(i % 20 == 0) {
            System.err.println();
         }
         if(i >= 'a' && i <= 'z') {
            System.err.print("1, ");
         } else if(i >= 'A' && i <= 'Z') {
            System.err.print("1, ");
         } else if(i >= '0' && i <= '9') {
            System.err.print("1, ");
         } else {
            System.err.print("0, ");
         }
      }
      System.err.println("};");
   }
}
