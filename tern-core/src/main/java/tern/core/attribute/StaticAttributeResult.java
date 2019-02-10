package tern.core.attribute;

import tern.core.constraint.Constraint;
import tern.core.scope.Scope;
import tern.core.type.Type;

public class StaticAttributeResult implements AttributeResult {
   
   private final Attribute attribute;

   public StaticAttributeResult(Attribute attribute) {
      this.attribute = attribute;
   }

   @Override
   public Constraint getConstraint(Scope scope, Constraint left, Type... types) throws Exception {
      return attribute.getConstraint();
   }
}