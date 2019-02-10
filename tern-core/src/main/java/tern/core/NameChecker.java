package tern.core;

public class NameChecker {
   
   private final boolean strict;
   
   public NameChecker(boolean strict) {
      this.strict = strict;
   }

   public boolean isEntity(String name) {
      int index = name.lastIndexOf(".");
      char first = name.charAt(index == -1 ? 0 : index + 1);
      
      if(strict) {
         return Character.isUpperCase(first) && Character.isAlphabetic(first);
      }
      return Character.isUpperCase(first);
   }
}
