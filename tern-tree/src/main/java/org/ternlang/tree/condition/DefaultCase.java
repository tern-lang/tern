package org.ternlang.tree.condition;

import org.ternlang.core.Evaluation;
import org.ternlang.core.Statement;
import org.ternlang.tree.StatementBlock;

public class DefaultCase implements Case {

   private final Statement statement;
   
   public DefaultCase(Statement... statements) {
      this.statement = new StatementBlock(statements);
   }
   
   @Override
   public Evaluation getEvaluation(){
      return null;
   }
   
   @Override
   public Statement getStatement(){
      return statement;
   }
}