package org.ternlang.core.error;

import org.ternlang.core.result.Result;
import org.ternlang.core.scope.Scope;

public class ExternalErrorHandler {

   public ExternalErrorHandler() {
      super();
   }

   public Result handleError(Scope scope, Object cause) throws Exception {
      if(Exception.class.isInstance(cause)) {
         throw (Exception)cause;
      }
      if(Throwable.class.isInstance(cause)) {
         throw new InternalException((Throwable)cause);
      }
      throw new InternalException(String.valueOf(cause));
   }
}