package tern.tree.function;

import tern.core.function.Connection;
import tern.core.function.Invocation;
import tern.core.function.bind.FunctionMatcher;
import tern.core.function.dispatch.FunctionDispatcher;
import tern.core.module.Module;
import tern.core.scope.Scope;
import tern.core.variable.Value;

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