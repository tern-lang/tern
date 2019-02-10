package tern.tree;

import static tern.core.result.Result.NORMAL;

import tern.core.Execution;
import tern.core.NoExecution;
import tern.core.Statement;
import tern.core.constraint.Constraint;
import tern.core.scope.Scope;
import tern.parse.StringToken;

public class EmptyStatement extends Statement {
   
   private final Execution execution;
   
   public EmptyStatement() {
      this(null);
   }
   
   public EmptyStatement(StringToken token) {
      this.execution = new NoExecution(NORMAL);
   }

   @Override
   public Execution compile(Scope scope, Constraint returns) throws Exception {
      return execution;
   }

}