package tern.tree.reference;

import static tern.core.constraint.Constraint.NONE;
import static tern.core.variable.Value.NULL;

import tern.core.Evaluation;
import tern.core.constraint.Constraint;
import tern.core.scope.Scope;
import tern.core.variable.Value;

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
