package tern.core.link;

import java.util.concurrent.FutureTask;

import tern.core.Execution;
import tern.core.error.InternalStateException;
import tern.core.module.Path;
import tern.core.result.Result;
import tern.core.scope.Scope;

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
