package org.ternlang.core.constraint.transform;

import org.ternlang.core.constraint.Constraint;

public class StaticParameterTransform implements ConstraintTransform{
   
   private final ConstraintRule reference;
   
   public StaticParameterTransform(Constraint constraint){
      this.reference = new ConstraintIndexRule(constraint);
   }
   
   @Override
   public ConstraintRule apply(Constraint left){
      return reference;
   }
}
