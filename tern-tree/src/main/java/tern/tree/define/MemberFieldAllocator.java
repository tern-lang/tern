package tern.tree.define;

import tern.core.Evaluation;
import tern.core.ModifierType;
import tern.core.scope.Scope;
import tern.core.variable.Value;
import tern.core.constraint.Constraint;
import tern.tree.DeclarationAllocator;

public class MemberFieldAllocator extends DeclarationAllocator {

   public MemberFieldAllocator(Constraint constraint, Evaluation expression) {   
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