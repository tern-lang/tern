package tern.tree.function;

import tern.core.function.Invocation;
import tern.core.function.InvocationBuilder;
import tern.core.scope.Scope;

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