package tern.core.convert;

import static tern.core.convert.Score.EXACT;
import static tern.core.convert.Score.SIMILAR;

import tern.core.type.Type;

public class NullConverter extends ConstraintConverter { 
   
   public NullConverter() {
      super();
   }

   @Override
   public Score score(Type type) throws Exception {
      if(type != null) {
         return SIMILAR; 
      }
      return EXACT;
   }
   
   @Override
   public Score score(Object value) throws Exception {
      if(value != null) {
         return SIMILAR; 
      }
      return EXACT; 
   }
   
   @Override
   public Object convert(Object object) {
      return object;
   }
}