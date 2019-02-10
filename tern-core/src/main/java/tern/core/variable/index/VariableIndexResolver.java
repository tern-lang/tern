package tern.core.variable.index;

import tern.core.Context;
import tern.core.module.Module;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.type.TypeExtractor;

public class VariableIndexResolver {
   
   public VariableIndexResolver() {
      super();
   }
   
   public int resolve(Scope scope, Object left){
      if(left != null) {
         Module module = scope.getModule();
         Context context = module.getContext();
         TypeExtractor extractor = context.getExtractor();
         Type type = extractor.getType(left);
         
         if(type != null) {
            return type.getOrder();
         }
      }
      return 0;
   }
}