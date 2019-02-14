package org.ternlang.core.error;

import org.ternlang.core.Context;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeExtractor;

public class ErrorTypeExtractor {
   
   public ErrorTypeExtractor() {
      super();
   }
   
   public Type extract(Scope scope, Object cause) {
      try {
         Module module = scope.getModule();
         Context context = module.getContext();
         TypeExtractor extractor = context.getExtractor();   
         
         if(InternalError.class.isInstance(cause)) {
            InternalError error = (InternalError)cause;
            Object value = error.getValue();
            
            return extractor.getType(value);
         }
         return extractor.getType(cause);
      } catch(Exception e) {
         return null;
      }
   }
}