package org.ternlang.core.function.index;

import org.ternlang.core.function.Function;
import org.ternlang.core.function.Invocation;
import org.ternlang.core.stack.ThreadStack;

public class TraceInvocationResolver {
   
   private TraceInvocationBuilder builder;
   private Invocation invocation;
   private Function function;
   
   public TraceInvocationResolver(ThreadStack stack, Function function) {
      this.builder = new TraceInvocationBuilder(stack);
      this.function = function;
   }
   
   public Invocation resolve() {
      if(invocation == null) {
         invocation = builder.create(function);
      }
      return invocation;
   }
}
