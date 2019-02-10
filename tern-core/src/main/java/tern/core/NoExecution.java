package tern.core;

import tern.core.result.Result;
import tern.core.scope.Scope;

public class NoExecution extends Execution {
   
   private final Result result;
   
   public NoExecution(Result result) {
      this.result = result;
   }

   @Override
   public Result execute(Scope scope) throws Exception {     
      return result;
   }
}
