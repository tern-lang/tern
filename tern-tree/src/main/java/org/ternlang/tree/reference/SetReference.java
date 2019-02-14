package org.ternlang.tree.reference;

import static org.ternlang.core.constraint.Constraint.SET;

import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.Value;
import org.ternlang.parse.StringToken;

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