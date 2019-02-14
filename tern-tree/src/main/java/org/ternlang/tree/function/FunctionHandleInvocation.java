package org.ternlang.tree.function;

import org.ternlang.core.function.Connection;
import org.ternlang.core.function.Invocation;
import org.ternlang.core.function.bind.FunctionMatcher;
import org.ternlang.core.function.dispatch.FunctionDispatcher;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.variable.Value;

public class FunctionHandleInvocation implements Invocation {

   private final FunctionHandleAligner aligner;
   private final FunctionMatcher matcher;
   private final Module module;
   private final Value value;

   public FunctionHandleInvocation(FunctionMatcher matcher, Module module, Value value) {
      this(matcher, module, value, false);
   }
   
   public FunctionHandleInvocation(FunctionMatcher matcher, Module module, Value value, boolean constructor) {
      this.aligner = new FunctionHandleAligner(value, constructor);
      this.matcher = matcher;
      this.module = module;
      this.value = value;
   }

   @Override
   public Object invoke(Scope scope, Object object, Object... list) throws Exception {
      Scope actual = module.getScope();
      Object[] arguments = aligner.align(list); // align constructor arguments
      FunctionDispatcher dispatcher = matcher.match(actual, value);
      Connection connection = dispatcher.connect(actual, value, arguments);
      
      return connection.invoke(actual, value, arguments);
   }
}