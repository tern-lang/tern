package tern.core.convert;

public class CharacterMatcher {

   public boolean matchCharacter(String text) {
      if(text != null) {
         int length = text.length();
         
         if(length == 1) {
            return true;
         }
      }
      return false;
   }
}