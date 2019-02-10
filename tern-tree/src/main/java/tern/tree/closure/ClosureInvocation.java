package tern.tree.closure;

import tern.core.function.Invocation;
import tern.core.function.InvocationBuilder;
import tern.core.scope.Scope;

public class ClosureInvocation implements Invocation<Object> {

   private final InvocationBuilder builder;
   private final Scope outer;
  
   public ClosureInvocation(InvocationBuilder builder, Scope outer) {
      this.builder = builder;
      this.outer = outer;
   }
   
   @Override
   public Object invoke(Scope scope, Object object, Object... list) throws Exception {
      Invocation invocation = builder.create(outer);
      return invocation.invoke(outer, object, list);
   }
}