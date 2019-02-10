package tern.core;

import static tern.core.result.Result.NORMAL;

import tern.core.constraint.Constraint;
import tern.core.scope.Scope;

public class NoStatement extends Statement {
   
   private final Execution execution;
   
   public NoStatement() {
      this.execution = new NoExecution(NORMAL);
   }

   @Override
   public Execution compile(Scope scope, Constraint returns) throws Exception {
      return execution;
   }
   
   
}