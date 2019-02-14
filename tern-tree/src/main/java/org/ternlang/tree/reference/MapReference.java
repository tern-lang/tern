package org.ternlang.tree.reference;

import static org.ternlang.core.constraint.Constraint.MAP;

import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.Value;
import org.ternlang.parse.StringToken;

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