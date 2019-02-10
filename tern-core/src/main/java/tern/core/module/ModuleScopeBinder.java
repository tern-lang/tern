package tern.core.module;

import tern.core.Context;
import tern.core.function.Function;
import tern.core.scope.Scope;
import tern.core.stack.ThreadStack;
import tern.core.type.Type;

public class ModuleScopeBinder {

   public ModuleScopeBinder() {
      super();
   }

   public Scope bind(Scope scope) {
      Module module = scope.getModule();
      Context context = module.getContext();
      ThreadStack stack = context.getStack();
      Function function = stack.current(); // we can determine the function type
      
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