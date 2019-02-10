package tern.core.constraint.transform;

import tern.core.attribute.Attribute;
import tern.core.constraint.Constraint;

public class AttributeTransform implements ConstraintTransform {
   
   private final ConstraintTransform transform;
   private final Attribute attribute;
   
   public AttributeTransform(ConstraintTransform transform, Attribute attribute) {
      this.transform = transform;
      this.attribute = attribute;
   }

   @Override
   public ConstraintRule apply(Constraint left) {
      ConstraintRule rule = transform.apply(left);
      return new AttributeRule(rule, attribute);
   }
}
