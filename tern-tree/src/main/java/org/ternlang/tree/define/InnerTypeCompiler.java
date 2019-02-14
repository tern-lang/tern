package org.ternlang.tree.define;

import org.ternlang.core.Statement;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeState;

public class InnerTypeCompiler extends TypeState {
   
   private final Statement statement;
   
   public InnerTypeCompiler(Statement statement) {
      this.statement = statement;
   }

   @Override
   public void compile(Scope scope, Type type) throws Exception {
      statement.compile(scope,  null);
   }
}
