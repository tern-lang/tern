package org.ternlang.core.module;

import org.ternlang.core.Context;
import org.ternlang.core.function.Function;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.stack.StackTrace;
import org.ternlang.core.stack.ThreadStack;
import org.ternlang.core.type.Type;

public class ModuleScopeBinder {

   public ModuleScopeBinder() {
      super();
   }

   public Scope bind(Scope scope) {
      Module module = scope.getModule();
      Context context = module.getContext();
      ThreadStack stack = context.getStack();
      StackTrace trace = stack.trace();
      Function function = trace.current(); // we can determine the function type
      
      if(function != null) {
         Type source = function.getSource();
         
         if(source != null) {
            Scope current = source.getScope();
            
            if(current != null) {
               return current;
            }
         }
      }
      return scope;
   }  
}