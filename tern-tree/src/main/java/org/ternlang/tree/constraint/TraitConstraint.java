package org.ternlang.tree.constraint;

import org.ternlang.core.Evaluation;
import org.ternlang.core.constraint.Constraint;

public class TraitConstraint extends ClassConstraint {
   
   public TraitConstraint(Evaluation evaluation) {
      super(evaluation);
   }
   
   public TraitConstraint(Evaluation evaluation, Constraint context) {
      super(evaluation, context);
   }
}
