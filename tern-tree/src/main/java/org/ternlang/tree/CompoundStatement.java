package org.ternlang.tree;

import org.ternlang.core.Compilation;
import org.ternlang.core.Statement;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.Path;

public class CompoundStatement implements Compilation {
   
   private final Statement[] statements;
   
   public CompoundStatement(Statement... statements) {
      this.statements = statements;
   }
   
   @Override
   public Object compile(Module module, Path path, int line) throws Exception {
      if(statements.length > 1) {
         return new StatementBlock(statements);
      }
      if(statements.length == 0) {
         return new EmptyStatement();
      }
      return statements[0];
   }  
}