package org.ternlang.core.constraint.transform;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;

public class AttributeTypeMapper {

   public AttributeTypeMapper() {
      super();
   }

   public Type map(Scope scope, Constraint constraint) {
      if(constraint != null) {
         Type type = constraint.getType(scope);

         if(type != null) {
            Class real = type.getType();

            if(real == Object.class) {
               return null;
            }
            if(real == void.class) {
               return null;
            }
            return type;
         }
      }
      return null;
   }
}
