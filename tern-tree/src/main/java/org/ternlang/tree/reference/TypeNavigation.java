package org.ternlang.tree.reference;

import static org.ternlang.core.constraint.Constraint.NONE;
import static org.ternlang.core.variable.Value.NULL;

import org.ternlang.core.Evaluation;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.variable.Value;

public abstract class TypeNavigation extends Evaluation {
   
   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      Value value = evaluate(scope, NULL);
      
      if(value != null) {
         return value.getConstraint();
      }
      return NONE;
   }
   
   public abstract String qualify(Scope scope, String left) throws Exception;
}
