package org.ternlang.tree.define;

import org.ternlang.core.function.Invocation;
import org.ternlang.core.function.InvocationBuilder;
import org.ternlang.core.scope.Scope;

public class StaticInvocation implements Invocation<Object> {

   private final InvocationBuilder builder;
   private final Scope inner;
   
   public StaticInvocation(InvocationBuilder builder, Scope inner) {
      this.builder = builder;
      this.inner = inner;
   }
   
   @Override
   public Object invoke(Scope outer, Object object, Object... list) throws Exception {
      Invocation invocation = builder.create(inner);   
      return invocation.invoke(inner, object, list);
   }
}