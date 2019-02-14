package org.ternlang.core.constraint;

import org.ternlang.core.Context;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeLoader;
import org.ternlang.core.variable.Value;

public class ValueConstraint extends Constraint {

   private final Value value;
   
   public ValueConstraint(Value value) {
      this.value = value;
   }
   
   @Override
   public Type getType(Scope scope){
      Object object = value.getValue();
      
      if(object != null) {
         Class require = object.getClass();
         Module module = scope.getModule();
         Context context = module.getContext();               
         TypeLoader loader = context.getLoader();
         
         return loader.loadType(require);
      }
      return null;
   }
}