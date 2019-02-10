package tern.tree.define;

import tern.core.Statement;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.type.TypeState;

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
