package tern.tree.reference;

import static java.util.Collections.EMPTY_LIST;

import tern.core.constraint.Constraint;
import tern.core.error.InternalStateException;
import tern.core.function.Signature;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.variable.Value;
import tern.tree.function.ParameterList;

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