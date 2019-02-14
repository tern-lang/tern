package org.ternlang.core.constraint;

import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;

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