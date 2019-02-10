package tern.core.convert;

import static tern.core.convert.Score.EXACT;
import static tern.core.convert.Score.INVALID;
import static tern.core.convert.Score.POSSIBLE;

import tern.core.scope.Scope;
import tern.core.type.Type;

public class ScopeConverter extends ConstraintConverter {
   
   public ScopeConverter() {
      super();
   }
   
   @Override
   public Score score(Type actual) throws Exception {
      if(actual != null) {
         Class real = actual.getType();
         
         if(Scope.class.isAssignableFrom(real)) { // what is this doing
            return EXACT;
         }
         return INVALID;
      }
      return POSSIBLE;
   }
   
   @Override
   public Score score(Object value) throws Exception {
      if(value != null) {
         if(Scope.class.isInstance(value)) {
            return EXACT;
         }
         return INVALID;
      }
      return POSSIBLE;
   }
   
   @Override
   public Object assign(Object object) {
      return object;
   }
   
   @Override
   public Object convert(Object object) {
      return object;
   }
}