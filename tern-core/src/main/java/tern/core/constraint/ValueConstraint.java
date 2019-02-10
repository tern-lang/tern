package tern.core.constraint;

import tern.core.Context;
import tern.core.module.Module;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.type.TypeLoader;
import tern.core.variable.Value;

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