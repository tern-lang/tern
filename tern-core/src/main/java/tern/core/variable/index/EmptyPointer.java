package tern.core.variable.index;

import static tern.core.constraint.Constraint.NONE;
import static tern.core.variable.Value.NULL;

import tern.core.constraint.Constraint;
import tern.core.scope.Scope;
import tern.core.variable.Value;

public class EmptyPointer implements VariablePointer {
   
   public EmptyPointer() {
      super();
   }

   @Override
   public Constraint getConstraint(Scope scope, Constraint left) {
      return NONE;
   }

   @Override
   public Value getValue(Scope scope, Object left) {
      return NULL;
   }
   

}
