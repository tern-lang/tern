package tern.core.constraint.transform;

import tern.core.constraint.Constraint;

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
