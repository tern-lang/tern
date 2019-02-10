package tern.core.error;

import tern.core.Context;
import tern.core.module.Module;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.type.TypeExtractor;

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