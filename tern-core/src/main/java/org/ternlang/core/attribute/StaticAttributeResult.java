package org.ternlang.core.attribute;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.scope.Scope;

public class StaticAttributeResult implements AttributeResult {
   
   private final Attribute attribute;

   public StaticAttributeResult(Attribute attribute) {
      this.attribute = attribute;
   }

   @Override
   public Constraint getConstraint(Scope scope, Constraint left, Constraint... types) throws Exception {
      return attribute.getConstraint();
   }
}