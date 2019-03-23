package org.ternlang.tree.math;

import org.ternlang.core.type.Type;

public class NumberChecker {

   public NumberChecker() {
      super();
   }

   public boolean isNumeric(Type type){
      if(type != null) {
         Class real = type.getType();

         if(real != null) {
            return isNumeric(real);
         }
      }
      return false;
   }

   public boolean isNumeric(Class type){
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
   
   public boolean isNumeric(Object value){
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

   public boolean isBothNumeric(Type left, Type right){
      if(left != null && right != null) {
         return isNumeric(left) && isNumeric(right);
      }
      return false;
   }
   
   public boolean isBothNumeric(Class left, Class right){
      if(left != null && right != null) {
         return isNumeric(left) && isNumeric(right);
      }
      return false;
   }
   
   public boolean isBothNumeric(Object left, Object right){
      if(left != null && right != null) {
         return isNumeric(left) && isNumeric(right);
      }
      return false;
   }
}