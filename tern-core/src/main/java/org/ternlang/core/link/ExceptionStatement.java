package org.ternlang.core.link;

import org.ternlang.core.Execution;
import org.ternlang.core.Statement;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.scope.Scope;

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