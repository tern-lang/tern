package org.ternlang.tree.closure;

import org.ternlang.core.function.Function;
import org.ternlang.core.function.FunctionBody;
import org.ternlang.core.function.Invocation;
import org.ternlang.core.function.InvocationBuilder;
import org.ternlang.core.scope.Scope;

public class ClosureBody extends FunctionBody {

   public ClosureBody(InvocationBuilder builder, InvocationBuilder other, Function function) {
      super(builder, other, function);
   }
   
   @Override
   public Function create(Scope scope) throws Exception {
      Invocation invocation = new ClosureInvocation(actual, scope);
      Function closure = new ClosureFunction(function, invocation);
      
      return closure;
   }
}
