package org.ternlang.tree.define;

import static org.ternlang.core.type.Category.OTHER;

import org.ternlang.core.function.Function;
import org.ternlang.core.function.FunctionBody;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Category;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeState;
import org.ternlang.tree.compile.TypeScopeCompiler;
import org.ternlang.tree.constraint.GenericList;

public class FunctionBodyCompiler extends TypeState {

   private final TypeScopeCompiler compiler;
   private final FunctionBody body;
   
   public FunctionBodyCompiler(GenericList generics, FunctionBody body) {
      this.compiler = new TypeScopeCompiler(generics);
      this.body = body;
   }

   @Override
   public Category define(Scope scope, Type type) throws Exception {
      return OTHER;
   }

   @Override
   public void compile(Scope scope, Type type) throws Exception {
      Function function = body.create(scope);
      Scope outer = compiler.compile(scope, type, function);

      body.compile(outer);
   }
}
