package tern.core.constraint;

import tern.core.Context;
import tern.core.module.Module;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.type.TypeLoader;

public class TypeNameConstraint extends Constraint {

   private String module;
   private String name;
   private Type type;
   
   public TypeNameConstraint(String name, String module) {
      this.module = module;
      this.name = name;
   }

   @Override
   public Type getType(Scope scope) {
      if(type == null) {
         Module parent = scope.getModule();
         Context context = parent.getContext();         
         TypeLoader loader = context.getLoader();
         
         type = loader.loadType(name, module);
      }
      return type;
   }
}
