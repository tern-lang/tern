package tern.tree.closure;

import tern.core.function.Function;
import tern.core.function.FunctionBody;
import tern.core.function.Invocation;
import tern.core.function.InvocationBuilder;
import tern.core.scope.Scope;

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
