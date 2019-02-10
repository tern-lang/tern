package tern.tree.define;

import static tern.core.type.Category.OTHER;

import tern.core.function.Function;
import tern.core.function.FunctionBody;
import tern.core.scope.Scope;
import tern.core.type.Category;
import tern.core.type.Type;
import tern.core.type.TypeState;
import tern.tree.compile.TypeScopeCompiler;
import tern.tree.constraint.GenericList;

public class FunctionBodyCompiler extends TypeState {

   private final TypeScopeCompiler compiler;
   private final FunctionBody body;
   
   public FunctionBodyCompiler(GenericList generics, FunctionBody body) {
      this.compiler = new TypeScopeCompiler(generics);
      this.body = body;
   }

   @Override
   public Category define(Scope scope, Type type) throws Exception {
      Scope outer = compiler.define(scope, type);

      body.define(outer);
      return OTHER;
   }

   @Override
   public void compile(Scope scope, Type type) throws Exception {
      Function function = body.create(scope);
      Scope outer = compiler.compile(scope, type, function);

      body.compile(outer);
   }
}
