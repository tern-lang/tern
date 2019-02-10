package tern.tree.define;

import tern.core.Statement;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.type.TypeBody;
import tern.core.type.TypePart;
import tern.core.type.TypeState;

public class InnerTypeDefinition extends TypePart {
   
   private final Statement statement;
   
   public InnerTypeDefinition(Statement statement) {
      this.statement = statement;
   }

   @Override
   public void create(TypeBody body, Type outer, Scope scope) throws Exception {
      statement.create(scope);
   }
   
   @Override
   public TypeState define(TypeBody body, Type outer, Scope scope) throws Exception {
      statement.define(scope);
      return new InnerTypeCompiler(statement);
   }
}