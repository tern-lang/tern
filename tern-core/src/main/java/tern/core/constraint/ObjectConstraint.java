package tern.core.constraint;

import tern.core.Context;
import tern.core.module.Module;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.type.TypeLoader;

public class ObjectConstraint extends Constraint {

   private final Object object;
   
   public ObjectConstraint(Object object) {
      this.object = object;
   }
   
   @Override
   public Type getType(Scope scope){
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