package org.ternlang.core.stack;

import org.ternlang.core.Context;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;

public class StackFrame {
   
   private StackTrace trace;
   private Scope scope;
   private boolean cache;
   
   public StackFrame(Scope scope) {
      this(scope, false);
   }
   
   public StackFrame(Scope scope, boolean cache) {
      this.cache = cache;
      this.scope = scope;
   }
   
   public StackTrace getTrace() {
      if(trace == null) {
         Module module = scope.getModule();
         Context context = module.getContext();
         ThreadStack stack = context.getStack();
         
         if(cache) {
            return trace = stack.trace();
         }
         return stack.trace();
      }
      return trace;
   }
}
