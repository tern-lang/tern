package org.ternlang.tree.define;

import org.ternlang.core.Statement;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeBody;
import org.ternlang.core.type.TypePart;
import org.ternlang.core.type.TypeState;

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