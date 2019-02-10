package tern.core.link;

import tern.core.error.InternalStateException;
import tern.core.scope.Scope;

public class ExceptionPackage implements Package {
   
   private final Exception cause;
   private final String message;
   
   public ExceptionPackage(String message, Exception cause) {
      this.message = message;
      this.cause = cause;
   }  
   
   @Override
   public PackageDefinition create(Scope scope) throws Exception {
      throw new InternalStateException(message, cause);
   }             
}