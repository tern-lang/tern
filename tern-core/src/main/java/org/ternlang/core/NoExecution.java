package org.ternlang.core;

import org.ternlang.core.result.Result;
import org.ternlang.core.scope.Scope;

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
