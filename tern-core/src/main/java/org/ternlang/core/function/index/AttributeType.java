package org.ternlang.core.function.index;

import static org.ternlang.core.constraint.Constraint.NONE;

import org.ternlang.core.attribute.AttributeResult;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.scope.Scope;

public class AttributeType implements ReturnType {

   private final AttributeResult result;
   private final Scope scope;

   public AttributeType(AttributeResult result, Scope scope) {
      this.result = result;
      this.scope = scope;
   }

   @Override   
   public Constraint check(Constraint left, Constraint[] types) throws Exception {
      if(result != null) {
         return result.getConstraint(scope, left, types);
      }
      return NONE;
   }
}
