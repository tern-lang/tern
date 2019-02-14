package org.ternlang.tree.resume;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.index.Local;
import org.ternlang.core.variable.Value;
import org.ternlang.tree.DeclarationAllocator;

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
