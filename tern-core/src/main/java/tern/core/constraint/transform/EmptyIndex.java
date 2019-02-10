package tern.core.constraint.transform;

import tern.core.constraint.Constraint;
import tern.core.scope.Scope;

public class EmptyIndex implements ConstraintIndex {
   
   public EmptyIndex() {
      super();
   }

   @Override
   public Constraint update(Scope scope, Constraint source, Constraint change) {
      return change;
   }
}
