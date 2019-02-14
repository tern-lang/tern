package org.ternlang.core.convert;

import java.util.Iterator;
import java.util.List;

import org.ternlang.core.ModifierType;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;

public class AliasResolver {

   public AliasResolver() {
      super();
   }

   public Type resolve(Type type) {
      if(type != null) {
         Scope scope = type.getScope();
         int modifiers = type.getModifiers();

         if (ModifierType.isAlias(modifiers)) {
            List<Constraint> types = type.getTypes();
            Iterator<Constraint> iterator = types.iterator();

            if (iterator.hasNext()) {
               Constraint constraint = iterator.next();
               Type next = constraint.getType(scope);

               return resolve(next);
            }
         }
      }
      return type;
   }
}
