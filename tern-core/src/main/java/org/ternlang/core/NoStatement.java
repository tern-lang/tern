package org.ternlang.core;

import static org.ternlang.core.result.Result.NORMAL;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.scope.Scope;

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