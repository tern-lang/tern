package org.ternlang.core.constraint.transform;

import org.ternlang.core.constraint.Constraint;

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
