package org.ternlang.core.constraint;

import org.ternlang.core.Context;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeLoader;

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