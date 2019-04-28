package org.ternlang.core.function.index;

import org.ternlang.core.function.Function;
import org.ternlang.core.function.Invocation;

public class TraceInvocationResolver {
   
   private TraceInvocationBuilder builder;
   private Invocation invocation;
   private Function function;
   
   public TraceInvocationResolver(Function function) {
      this.builder = new TraceInvocationBuilder();
      this.function = function;
   }
   
   public Invocation resolve() {
      if(invocation == null) {
         invocation = builder.create(function);
      }
      return invocation;
   }
}
