package org.ternlang.core.variable.index;

import static org.ternlang.core.constraint.Constraint.NONE;
import static org.ternlang.core.variable.Value.NULL;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.variable.Value;

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
