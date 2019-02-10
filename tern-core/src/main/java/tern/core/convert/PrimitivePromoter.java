package tern.core.convert;

public class PrimitivePromoter {

   public PrimitivePromoter() {
      super();
   }
   
   public Class promote(Class type) {
      if(type == int.class) {
         return Integer.class;
      }
      if(type == double.class) {
         return Double.class;
      }
      if(type == float.class) {
         return Float.class;
      }
      if(type == boolean.class) {
         return Boolean.class;
      }
      if(type == byte.class) {
         return Byte.class;
      }
      if(type == short.class) {
         return Short.class;
      }
      if(type == long.class) {
         return Long.class;
      }
      if(type == char.class) {
         return Character.class;
      }
      if(type == void.class) {
         return Void.class;
      }
      return type;
   }
}