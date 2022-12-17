package org.ternlang.tree.define;

import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.TypePart;
import org.ternlang.tree.constraint.ClassName;

public class ClassBody {

   private final TypePart[] parts;
   private final ClassName name;

   public ClassBody(ClassName name, TypePart... parts) {
      this.parts = parts;
      this.name = name;
   }

   public TypePart[] getParts(Scope scope) throws Exception {
      TypePart constructor = name.getConstructor(scope);

      if(constructor != null) {
         TypePart[] copy = new TypePart[parts.length + 1];

         for(int i = 0; i < parts.length; i++) {
            copy[i + 1] = parts[i];
         }
         copy[0] = constructor;
         return copy;
      }
      return parts;
   }
}
