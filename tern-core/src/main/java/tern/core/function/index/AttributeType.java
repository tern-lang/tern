package tern.core.function.index;

import static tern.core.constraint.Constraint.NONE;

import tern.core.attribute.AttributeResult;
import tern.core.constraint.Constraint;
import tern.core.scope.Scope;
import tern.core.type.Type;

public class AttributeType implements ReturnType {

   private final AttributeResult result;
   private final Scope scope;

   public AttributeType(AttributeResult result, Scope scope) {
      this.result = result;
      this.scope = scope;
   }

   @Override   
   public Constraint check(Constraint left, Type[] types) throws Exception {
      if(result != null) {
         return result.getConstraint(scope, left, types);
      }
      return NONE;
   }
}
