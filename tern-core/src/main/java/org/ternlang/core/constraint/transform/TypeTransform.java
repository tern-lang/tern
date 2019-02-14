package org.ternlang.core.constraint.transform;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.constraint.StaticConstraint;
import org.ternlang.core.type.Type;

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
