package org.ternlang.core.constraint.transform;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.scope.Scope;

public class EmptyIndex implements ConstraintIndex {
   
   public EmptyIndex() {
      super();
   }

   @Override
   public Constraint update(Scope scope, Constraint source, Constraint change) {
      return change;
   }
}
