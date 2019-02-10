package tern.tree.reference;

import static tern.core.constraint.Constraint.MAP;

import tern.core.error.InternalStateException;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.variable.Value;
import tern.parse.StringToken;

public class MapReference extends ConstraintReference {
   
   public MapReference(StringToken token) {
      super();
   }
   
   @Override
   protected ConstraintValue create(Scope scope) {
      try {
         Type type = MAP.getType(scope);
         Value reference = Value.getReference(type);
         
         return new ConstraintValue(MAP, reference, type);
      } catch(Exception e) {
         throw new InternalStateException("Could not resolve map reference", e);
      }
   }
}