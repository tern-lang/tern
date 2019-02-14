package org.ternlang.tree.constraint;

import org.ternlang.core.Evaluation;
import org.ternlang.core.constraint.Constraint;

public class ClassConstraint extends TypeConstraint {
   
   public ClassConstraint(Evaluation evaluation) {
      super(evaluation);
   }
   
   public ClassConstraint(Evaluation evaluation, Constraint context) {
      super(evaluation, context);
   }
}
