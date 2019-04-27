package org.ternlang.core.function.index;

import static org.ternlang.core.constraint.Constraint.NONE;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.convert.proxy.ProxyWrapper;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.bind.VariableFinder;
import org.ternlang.core.variable.bind.VariableResult;

public class LocalTypeFinder {

   private final VariableFinder finder;
   
   public LocalTypeFinder(ProxyWrapper wrapper) {
      this.finder = new VariableFinder(wrapper);
   }
   
   public Type findType(Scope scope, String name) {
      Module module = scope.getModule();
      Type type = module.getType(name); 
      
      if(type == null) {
         VariableResult result = finder.findType(scope, name); // slow but thorough
         
         if(result != null) {
            Constraint constraint = result.getConstraint(NONE);
            
            if(constraint != null) {
               return constraint.getType(scope);
            }
         }
      }
      return type;
   }
}
