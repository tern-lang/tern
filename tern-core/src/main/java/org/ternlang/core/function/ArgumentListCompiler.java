package org.ternlang.core.function;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;

public class ArgumentListCompiler {
   
   private final Type[] empty;
   
   public ArgumentListCompiler() {
      this.empty = new Type[] {};
   }

   public Type[] compile(Scope scope, Constraint... types) throws Exception {
      if(types.length > 0) {
         Type[] list = new Type[types.length];
         
         for(int i = 0; i < list.length; i++) {
            list[i] = types[i].getType(scope);
         }
         return list;
      }
      return empty;
   }
}
