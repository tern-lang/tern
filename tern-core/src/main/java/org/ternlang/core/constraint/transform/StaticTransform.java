package org.ternlang.core.constraint.transform;

import org.ternlang.core.constraint.Constraint;

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
