package tern.tree.define;

import tern.core.scope.Scope;
import tern.core.type.TypeState;
import tern.core.type.Type;
import tern.core.result.Result;

public class PrimitiveState extends TypeState {

   @Override
   public Result execute(Scope scope, Type type) throws Exception {  
      Scope outer = scope.getScope();
      return Result.getNormal(outer);
   }

}