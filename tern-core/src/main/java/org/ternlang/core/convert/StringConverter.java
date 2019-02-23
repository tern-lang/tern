package org.ternlang.core.convert;

import static org.ternlang.core.convert.Score.EXACT;
import static org.ternlang.core.convert.Score.INVALID;
import static org.ternlang.core.convert.Score.POSSIBLE;

import org.ternlang.core.type.Type;

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
         if(real == Character.class) {
            return POSSIBLE;
         }
         return INVALID;
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
         if(type == Character.class) {
            return POSSIBLE;
         }
         return INVALID;
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