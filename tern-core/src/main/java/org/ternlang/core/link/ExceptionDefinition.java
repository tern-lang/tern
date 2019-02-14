package org.ternlang.core.link;

import org.ternlang.core.Statement;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.module.Path;
import org.ternlang.core.scope.Scope;

public class ExceptionDefinition implements PackageDefinition {
   
   private final Exception cause;
   private final String message;
   
   public ExceptionDefinition(String message, Exception cause) {
      this.message = message;
      this.cause = cause;
   }

   @Override
   public Statement define(Scope scope, Path from) throws Exception {
      throw new InternalStateException(message, cause);
   }
}