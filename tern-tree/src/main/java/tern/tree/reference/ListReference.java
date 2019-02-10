package tern.tree.reference;

import static tern.core.constraint.Constraint.LIST;

import tern.core.error.InternalStateException;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.variable.Value;
import tern.parse.StringToken;

public class ListReference extends ConstraintReference {
   
   public ListReference(StringToken token) {
      super();
   }
   
   @Override
   protected ConstraintValue create(Scope scope) {
      try {
         Type type = LIST.getType(scope);
         Value reference = Value.getReference(type);
         
         return new ConstraintValue(LIST, reference, type);
      } catch(Exception e) {
         throw new InternalStateException("Could not resolve list reference", e);
      }
   }
}