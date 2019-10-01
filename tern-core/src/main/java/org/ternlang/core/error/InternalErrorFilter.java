package org.ternlang.core.error;

import static org.ternlang.core.Reserved.PROPERTY_CAUSE;
import static org.ternlang.core.stack.StackTraceType.EXCLUDE_ALL;

import java.util.List;

import org.ternlang.core.stack.StackTraceFilter;
import org.ternlang.core.type.index.FieldUpdater;

public class InternalErrorFilter {
 
   private final ErrorCauseExtractor extractor;
   private final FieldUpdater updater;
   private final StackTraceFilter filter;
   private final StackTraceElement[] empty;
   private final boolean replace;
   
   public InternalErrorFilter(boolean replace) {
      this.updater = new FieldUpdater(Throwable.class, PROPERTY_CAUSE);
      this.filter = new StackTraceFilter(EXCLUDE_ALL, 1024);
      this.extractor = new ErrorCauseExtractor();
      this.empty = new StackTraceElement[]{};
      this.replace = replace;
   }

   public InternalError filter(Object value, Throwable original, String... filters) {
      if(replace) {
         Throwable source = extract(value, filters);
         Throwable cause = extract(original, filters);
         
         if(source != null && cause != null) {
            return new InternalError(source, cause);
         } 
         if(source != null) {
            return new InternalError(source, original);
         }
         if(cause != null) {
            return new InternalError(value, cause);
         }
      }
      return new InternalError(value, original);
   }
   
   private Throwable extract(Object original, String... filters) {
      Throwable cause = extractor.extractCause(original);
      
      if(cause != null) {
         List<StackTraceElement> list = filter.filter(cause, filters);
         StackTraceElement[] trace = list.toArray(empty);
         Throwable child = cause.getCause();
         Object inner = extract(child, filters);
         
         if(trace.length > 0) {
            cause.setStackTrace(trace);
         }
         if(inner != child) {
            updater.update(cause, inner);
         }
      }
      return cause;
   }
}
