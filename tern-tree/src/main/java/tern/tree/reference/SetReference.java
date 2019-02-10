package tern.tree.reference;

import static tern.core.constraint.Constraint.SET;

import tern.core.error.InternalStateException;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.variable.Value;
import tern.parse.StringToken;

public class SetReference extends ConstraintReference {
   
   public SetReference(StringToken token) {
      super();
   }
   
   @Override
   protected ConstraintValue create(Scope scope) {
      try {
         Type type = SET.getType(scope);
         Value reference = Value.getReference(type);
         
         return new ConstraintValue(SET, reference, type);
      } catch(Exception e) {
         throw new InternalStateException("Could not resolve set reference", e);
      }
   }
}