package org.ternlang.core.function;

import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.trace.Trace;
import org.ternlang.core.trace.TraceInterceptor;

public class TraceAccessor implements Accessor {

   private final TraceInterceptor interceptor;
   private final Accessor accessor;
   private final Module module;
   private final Trace trace;

   public TraceAccessor(TraceInterceptor interceptor, Accessor accessor, Module module, Trace trace) {
      this.interceptor = interceptor;
      this.accessor = accessor;
      this.module = module;
      this.trace = trace;
   }

   @Override
   public Object getValue(Object source) {
      Scope scope = module.getScope();

      try {
         interceptor.traceBefore(scope, trace);
         return accessor.getValue(source);
      } finally {
         interceptor.traceAfter(scope, trace);
      }
   }

   @Override
   public void setValue(Object source, Object value) {
      accessor.setValue(source, value);
   }
}
