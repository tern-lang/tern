package org.ternlang.tree.define;

import org.ternlang.core.Evaluation;
import org.ternlang.core.ModifierType;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.variable.Value;
import org.ternlang.tree.DeclarationAllocator;

public class ModulePropertyAllocator extends DeclarationAllocator {

   public ModulePropertyAllocator(Constraint constraint, Evaluation expression) {   
      super(constraint, expression);
   }
   
   @Override
   protected <T extends Value> T declare(Scope scope, String name, Constraint type, int modifiers) throws Exception {
      if(ModifierType.isConstant(modifiers)) {
         return (T)Value.getBlank(null, type, modifiers);
      }
      return (T)Value.getProperty(null, type, modifiers);
   }
   
   @Override
   protected <T extends Value> T assign(Scope scope, String name, Object value, Constraint type, int modifiers) throws Exception {
      if(ModifierType.isConstant(modifiers)) {
         return (T)Value.getConstant(value, type, modifiers);
      }
      return (T)Value.getProperty(value, type, modifiers);
   }
}