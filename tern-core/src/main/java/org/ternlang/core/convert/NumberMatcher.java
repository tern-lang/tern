package org.ternlang.core.convert;

public class NumberMatcher {

   public NumberType matchNumber(String text) {
      if(text != null) {
         int length = text.length();
         
         if(length > 0) {
            char first = text.charAt(0);
            
            if(first == '-' || first == '+') {
               if(length > 1) {
                  char second = text.charAt(2);
                  
                  if(second == 'x') {
                     return matchHexidecimal(text, 3, length);
                  }
               }
               return matchDecimal(text, 1, length);
            }
            if(first == '0' && length > 1) {
               char second = text.charAt(1);
               
               if(second == 'x') {
                  return matchHexidecimal(text, 2, length);
               }
            }
            return matchDecimal(text, 0, length);
         }
      }
      return NumberType.NONE;
   }
   
   private NumberType matchHexidecimal(String text, int index, int length) {
      while(index < length) {
         char value = text.charAt(index++);
         
         if(value < '0' || value > '9') {
            if(value  < 'a' || value > 'f') {
               if(value < 'A' || value > 'F') {
                  return NumberType.NONE;
               }
            }
         }
      }
      return NumberType.HEXIDECIMAL;
   }
   
   private NumberType matchDecimal(String text, int index, int length) {
      while(index < length) {
         char value = text.charAt(index++);
         
         if(value == '-' || value == '+') {
            if(index > 1) {
               return NumberType.NONE;
            }
         } else if(value < '0' || value > '9') {
            if(value == '.') {
               break;
            }
            return NumberType.NONE;
         } 
      }
      if(index < length) { // must be '.'
         while(index < length) {
            char value = text.charAt(index++);
            
            if(value < '0' || value > '9') {                     
               if(value == 'e' || value == 'E') {
                  break;
               }
               return NumberType.NONE;
            }
         }
         int start = index + 1;
         
         while(index < length) {
            char value = text.charAt(index++);
            
            if(value == '-' || value == '+') {
               if(start > index) {
                  return NumberType.NONE;
               }
            } else if(value < '0' || value > '9') {
               return NumberType.NONE;
            } 
         }
      }
      return NumberType.DECIMAL;
   }
}