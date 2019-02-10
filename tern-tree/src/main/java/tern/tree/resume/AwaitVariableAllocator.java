package tern.tree.resume;

import tern.core.constraint.Constraint;
import tern.core.scope.Scope;
import tern.core.scope.index.Local;
import tern.core.variable.Value;
import tern.tree.DeclarationAllocator;

public class AwaitVariableAllocator extends DeclarationAllocator {

   public AwaitVariableAllocator(Constraint constraint) {
      super(constraint, null);
   }

   protected <T extends Value> T declare(Scope scope, String name, Constraint type, int modifiers) throws Exception {
      return (T)Local.getReference(null, name, type);
   }

   protected <T extends Value> T assign(Scope scope, String name, Object value, Constraint type, int modifiers) throws Exception {
      return (T)Local.getReference(value, name, type);
   }
}
