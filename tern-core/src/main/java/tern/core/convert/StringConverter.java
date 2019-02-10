package tern.core.convert;

import static tern.core.convert.Score.EXACT;
import static tern.core.convert.Score.POSSIBLE;

import tern.core.type.Type;

public class StringConverter extends ConstraintConverter {
   
   public StringConverter() {
      super();
   }
   
   @Override
   public Score score(Type actual) throws Exception {
      if(actual != null) {
         Class real = actual.getType();
         
         if(real == String.class) {
            return EXACT;
         }
      }
      return POSSIBLE;
   }
   
   @Override
   public Score score(Object value) throws Exception {
      if(value != null) {
         Class type = value.getClass();
      
         if(type == String.class) {
            return EXACT;
         }
      }
      return POSSIBLE;
   }
   
   @Override
   public Object assign(Object object) throws Exception {
      if(object != null) {
         return object.toString();
      }
      return null;
   }
   
   @Override
   public Object convert(Object object) throws Exception {
      if(object != null) {
         return object.toString();
      }
      return null;
   }
}