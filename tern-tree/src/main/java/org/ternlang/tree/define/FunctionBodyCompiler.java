package org.ternlang.tree.define;

import static org.ternlang.core.type.Category.OTHER;

import org.ternlang.core.function.Function;
import org.ternlang.core.function.FunctionBody;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Category;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeState;
import org.ternlang.tree.compile.ScopeCompiler;

public class FunctionBodyCompiler extends TypeState {

   private final ScopeCompiler compiler;
   private final FunctionBody body;
   
   public FunctionBodyCompiler(FunctionBody body, ScopeCompiler compiler) {
      this.compiler = compiler;
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
