package tern.core.link;

import tern.core.Execution;
import tern.core.Statement;
import tern.core.constraint.Constraint;
import tern.core.error.InternalStateException;
import tern.core.scope.Scope;

public class ExceptionStatement extends Statement {
   
   private final Exception cause;
   private final String message;
   
   public ExceptionStatement(String message, Exception cause) {
      this.message = message;
      this.cause = cause;
   }
   
   @Override
   public void create(Scope scope) throws Exception {
      throw new InternalStateException(message, cause);
   }
                  
   @Override
   public boolean define(Scope scope) throws Exception {
      throw new InternalStateException(message, cause);
   }

   @Override
   public Execution compile(Scope scope, Constraint returns) throws Exception {
      throw new InternalStateException(message, cause);
   }
}