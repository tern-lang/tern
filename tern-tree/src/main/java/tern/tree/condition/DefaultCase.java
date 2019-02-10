package tern.tree.condition;

import tern.core.Evaluation;
import tern.core.Statement;
import tern.tree.StatementBlock;

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