package org.ternlang.core.attribute;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;

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