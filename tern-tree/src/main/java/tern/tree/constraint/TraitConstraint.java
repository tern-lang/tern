package tern.tree.constraint;

import tern.core.Evaluation;
import tern.core.constraint.Constraint;

public class TraitConstraint extends ClassConstraint {
   
   public TraitConstraint(Evaluation evaluation) {
      super(evaluation);
   }
   
   public TraitConstraint(Evaluation evaluation, Constraint context) {
      super(evaluation, context);
   }
}
