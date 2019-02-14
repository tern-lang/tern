package org.ternlang.tree.closure;

import org.ternlang.core.function.Invocation;
import org.ternlang.core.function.InvocationBuilder;
import org.ternlang.core.scope.Scope;

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