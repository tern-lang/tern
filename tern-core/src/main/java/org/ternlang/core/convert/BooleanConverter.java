package org.ternlang.core.convert;

import static org.ternlang.core.convert.Score.EXACT;
import static org.ternlang.core.convert.Score.INVALID;
import static org.ternlang.core.convert.Score.POSSIBLE;

import org.ternlang.core.error.InternalArgumentException;
import org.ternlang.core.type.Type;

public class BooleanConverter extends ConstraintConverter {

   private final BooleanMatcher matcher;
   private final Type type;
   
   public BooleanConverter(Type type) {
      this.matcher = new BooleanMatcher();
      this.type = type;
   }
   
   @Override
   public Score score(Type actual) throws Exception {
      if(actual != null) {
         Class real = actual.getType();
         
         if(real != null) {
            if(real == Boolean.class) {
               return EXACT;
            }
            if(real == boolean.class) {
               return EXACT;
            }
         }
         return INVALID;
      }
      return POSSIBLE;
   }
   
   @Override
   public Score score(Object value) throws Exception {
      Class require = type.getType();
      
      if(value != null) {
         Class actual = value.getClass();
         
         if(actual == Boolean.class) {
            return EXACT;
         }
         return INVALID;
      }
      if(require.isPrimitive()) {
         return INVALID;
      }
      return POSSIBLE;
   }
   
   @Override
   public Object convert(Object value) throws Exception {
      Class require = type.getType();
      
      if(value != null) {
         Class actual = value.getClass();

         if(actual == Boolean.class) {
            return value;
         }
         throw new InternalArgumentException("Conversion from " + actual + " to boolean is not possible");
      }
      if(require.isPrimitive()) {
         throw new InternalArgumentException("Invalid conversion from null to primitive boolean");
      }
      return null;
   }
}