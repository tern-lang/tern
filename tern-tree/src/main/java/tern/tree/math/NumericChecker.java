package tern.tree.math;

import tern.core.type.Type;

public class NumericChecker {

   public static boolean isNumeric(Type type){
      Class real = type.getType();
      return Number.class.isAssignableFrom(real) || Character.class.isAssignableFrom(real);
   }
   
   public static boolean isNumeric(Class type){
      return Number.class.isAssignableFrom(type) || Character.class.isAssignableFrom(type);
   }
   
   public static boolean isNumeric(Object value){
      return Number.class.isInstance(value) || Character.class.isInstance(value);
   }

   public static boolean isBothNumeric(Type left, Type right){
      return isNumeric(left) && isNumeric(right);
   }
   
   public static boolean isBothNumeric(Class left, Class right){
      return isNumeric(left) && isNumeric(right);
   }
   
   public static boolean isBothNumeric(Object left, Object right){
      return isNumeric(left) && isNumeric(right);
   }
}