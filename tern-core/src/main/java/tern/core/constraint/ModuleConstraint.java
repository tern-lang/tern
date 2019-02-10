package tern.core.constraint;

import tern.core.module.Module;
import tern.core.scope.Scope;
import tern.core.type.Type;

public class ModuleConstraint extends Constraint {
   
   private final Module module;
   
   public ModuleConstraint(Module module) {
      this.module = module;
   }
   
   @Override
   public Type getType(Scope scope){
      return module.getType();
   }
   
   @Override
   public boolean isModule() {
      return true;
   }
   
   @Override
   public boolean isConstant() {
      return true;
   }
   
   @Override
   public String toString(){
      return String.valueOf(module);
   }
}