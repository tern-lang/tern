package org.ternlang.core.link;

import org.ternlang.core.Execution;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.result.Result;
import org.ternlang.core.scope.Scope;

public class ExceptionExecution extends Execution {

   private final Exception cause;
   private final String message;

   public ExceptionExecution(String message, Exception cause) {
      this.message = message;
      this.cause = cause;
   }

   @Override
   public Result execute(Scope scope) throws Exception {
      throw new InternalStateException(message, cause);
   }
}
