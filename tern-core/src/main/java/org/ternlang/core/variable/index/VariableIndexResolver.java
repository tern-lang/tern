package org.ternlang.core.variable.index;

import org.ternlang.core.Context;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeExtractor;

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