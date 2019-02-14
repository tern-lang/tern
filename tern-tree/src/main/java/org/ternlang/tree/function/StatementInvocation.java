package org.ternlang.tree.function;

import org.ternlang.core.function.Invocation;
import org.ternlang.core.function.InvocationBuilder;
import org.ternlang.core.scope.Scope;

public class StatementInvocation implements Invocation<Object> {

   private final InvocationBuilder builder;

   public StatementInvocation(InvocationBuilder builder) {
      this.builder = builder;
   }
   
   @Override
   public Object invoke(Scope scope, Object object, Object... list) throws Exception {
      Scope outer = scope.getScope(); 
      Invocation invocation = builder.create(outer); // what if the body is compiled
      
      return invocation.invoke(outer, object, list);
   }
}