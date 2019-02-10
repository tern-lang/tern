package tern.tree.constraint;

import tern.core.Evaluation;
import tern.core.constraint.Constraint;

public class ClassConstraint extends TypeConstraint {
   
   public ClassConstraint(Evaluation evaluation) {
      super(evaluation);
   }
   
   public ClassConstraint(Evaluation evaluation, Constraint context) {
      super(evaluation, context);
   }
}
