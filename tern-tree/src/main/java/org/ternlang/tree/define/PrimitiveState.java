package org.ternlang.tree.define;

import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.TypeState;
import org.ternlang.core.type.Type;
import org.ternlang.core.result.Result;

public class PrimitiveState extends TypeState {

   @Override
   public Result execute(Scope scope, Type type) throws Exception {  
      Scope outer = scope.getParent();
      return Result.getNormal(outer);
   }

}