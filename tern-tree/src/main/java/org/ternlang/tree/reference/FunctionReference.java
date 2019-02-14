package org.ternlang.tree.reference;

import static java.util.Collections.EMPTY_LIST;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.function.Signature;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.Value;
import org.ternlang.tree.function.ParameterList;

public class FunctionReference extends ConstraintReference {
   
   private final ParameterList list;
   
   public FunctionReference(ParameterList list) {
      this.list = list;
   }
   
   @Override
   protected ConstraintValue create(Scope scope) {
      try {
         Signature signature = list.create(scope, EMPTY_LIST);
         Type type = signature.getDefinition();
         Constraint constraint = Constraint.getConstraint(type);
         Value reference = Value.getReference(type);
         
         return new ConstraintValue(constraint, reference, type);
      } catch(Exception e) {
         throw new InternalStateException("Invalid function reference", e);
      }
   }
}