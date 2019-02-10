package tern.core.constraint.transform;

import tern.core.constraint.Constraint;

public class IdentityTransform implements ConstraintTransform{
   
   private final ConstraintIndex index;
   
   public IdentityTransform(ConstraintIndex index){
      this.index = index;
   }
   
   @Override
   public ConstraintRule apply(Constraint left){
      return new ConstraintIndexRule(left, index);
   }
}
