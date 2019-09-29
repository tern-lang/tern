package org.ternlang.core.error;

import static org.ternlang.core.stack.OriginTraceType.EXCLUDE_ALL;

import java.util.List;

import org.ternlang.core.stack.OriginTraceFilter;

public class InternalErrorFilter {

   private final ErrorCauseExtractor extractor;
   private final OriginTraceFilter filter;
   private final StackTraceElement[] empty;
   private final boolean replace;
   
   public InternalErrorFilter(boolean replace) {
      this.filter = new OriginTraceFilter(EXCLUDE_ALL, 1024);
      this.extractor = new ErrorCauseExtractor();
      this.empty = new StackTraceElement[]{};
      this.replace = replace;
   }
   
   public InternalError filter(Object value, Throwable original, String... filters) {
      if(replace) {
         Throwable cause = extractor.extractCause(original);
         
         if(cause != null) {
            List<StackTraceElement> list = filter.filter(cause, filters);
            StackTraceElement[] trace = list.toArray(empty);
            Throwable child = cause.getCause();
            
            if(trace.length > 0) {
               cause.setStackTrace(trace);
            }
            filter(value, child, filters);
            
            return new InternalError(value, cause);
         }
      }
      return new InternalError(value, original);
   }
}
