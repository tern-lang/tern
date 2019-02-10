package tern.core.constraint.transform;

import tern.core.constraint.Constraint;
import tern.core.constraint.StaticConstraint;
import tern.core.type.Type;

public class TypeTransform implements ConstraintTransform {
   
   private final ConstraintRule reference;
   private final Constraint constriant;
   
   public TypeTransform(Type type) {
      this.constriant = new StaticConstraint(type);
      this.reference = new ConstraintIndexRule(constriant);
   }

   @Override
   public ConstraintRule apply(Constraint left) {
      return reference;
   }

}
