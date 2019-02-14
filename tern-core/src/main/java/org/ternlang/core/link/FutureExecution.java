package org.ternlang.core.link;

import java.util.concurrent.FutureTask;

import org.ternlang.core.Execution;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.module.Path;
import org.ternlang.core.result.Result;
import org.ternlang.core.scope.Scope;

public class FutureExecution extends Execution {
   
   private final FutureTask<Execution> result;
   private final Path path;
   
   public FutureExecution(FutureTask<Execution> result, Path path) {
      this.result = result;
      this.path = path;
   }

   @Override
   public Result execute(Scope scope) throws Exception {
      Execution execution = result.get();
      
      if(execution == null) {
         throw new InternalStateException("Could not execute '" + path + "'");
      }
      return execution.execute(scope);
   }

}
