package org.ternlang.core.error;

import org.ternlang.core.result.Result;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.stack.ThreadStack;
import org.ternlang.core.trace.Trace;
import org.ternlang.core.type.TypeExtractor;

public class InternalErrorHandler {

   private final InternalErrorFormatter formatter;
   private final InternalErrorBuilder builder;
   
   public InternalErrorHandler(TypeExtractor extractor, ThreadStack stack) {
      this(extractor, stack, true);
   }
   
   public InternalErrorHandler(TypeExtractor extractor, ThreadStack stack, boolean replace) {
      this.builder = new InternalErrorBuilder(stack, replace);
      this.formatter = new InternalErrorFormatter();
   }
   
   public Result handleError(Scope scope, Object value) {
      throw builder.createInternalError(value, null);
   }
   
   public Result handleError(Scope scope, Throwable cause, Trace trace) {
      Throwable exception = formatter.formatInternalError(cause, trace);
      throw builder.createInternalError(cause, exception);
   }
}