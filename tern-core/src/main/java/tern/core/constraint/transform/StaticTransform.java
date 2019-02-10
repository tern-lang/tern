package tern.core.constraint.transform;

import tern.core.constraint.Constraint;

public class StaticTransform implements ConstraintTransform{
   
   private final ConstraintRule reference;
   
   public StaticTransform(Constraint constraint, ConstraintIndex index){
      this.reference = new ConstraintIndexRule(constraint, index);
   }
   
   @Override
   public ConstraintRule apply(Constraint left){
      return reference;
   }
}
