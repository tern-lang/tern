package org.ternlang.tree.math;

import org.ternlang.core.type.Type;

public class NumberChecker {

   public static boolean isNumeric(Type type){
      if(type != null) {
         Class real = type.getType();

         if(Number.class.isAssignableFrom(real)) {
            return true;
         }
         if(Character.class.isAssignableFrom(real)) {
            return true;
         }
      }
      return false;
   }
   
   public static boolean isNumeric(Class type){
      if(type != null) {
         if (Number.class.isAssignableFrom(type)) {
            return true;
         }
         if (Character.class.isAssignableFrom(type)) {
            return true;
         }
      }
      return false;
   }
   
   public static boolean isNumeric(Object value){
      if(value != null) {
         if (Number.class.isInstance(value)) {
            return true;
         }
         if (Character.class.isInstance(value)) {
            return true;
         }
      }
      return false;
   }

   public static boolean isBothNumeric(Type left, Type right){
      if(left != null && right != null) {
         return isNumeric(left) && isNumeric(right);
      }
      return false;
   }
   
   public static boolean isBothNumeric(Class left, Class right){
      if(left != null && right != null) {
         return isNumeric(left) && isNumeric(right);
      }
      return false;
   }
   
   public static boolean isBothNumeric(Object left, Object right){
      if(left != null && right != null) {
         return isNumeric(left) && isNumeric(right);
      }
      return false;
   }
}