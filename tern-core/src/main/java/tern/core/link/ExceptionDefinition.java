package tern.core.link;

import tern.core.Statement;
import tern.core.error.InternalStateException;
import tern.core.module.Path;
import tern.core.scope.Scope;

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