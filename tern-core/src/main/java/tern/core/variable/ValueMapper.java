package tern.core.variable;

import tern.core.error.InternalStateException;

public class ValueMapper {

   public static Character toCharacter(Object value) {
      return toCharacter(value, null);
   }

   public static Number toNumber(Object value) {
      return toNumber(value, null);
   }
   
   private static Character toCharacter(Object value, Exception cause) {
      try {
         return (Character)value;
      } catch(Exception e) {
         if(cause == null) {
            return (char)toNumber(value, e).intValue();
         }
         throw new InternalStateException("Conversion failure for " + value, cause);
      }
   }
   
   private static Number toNumber(Object value, Exception cause) {
      try {
         return (Number)value;
      } catch(Exception e) {
         if(cause == null) {
            return (int)toCharacter(value, e).charValue();
         }
         throw new InternalStateException("Conversion failure for " + value, cause);
      }
   }
}
