package org.ternlang.core.error;

import static org.ternlang.core.stack.OriginTraceType.EXCLUDE_ALL;

import java.lang.reflect.Field;
import java.util.List;

import org.ternlang.core.stack.OriginTraceFilter;

public class InternalErrorFilter {
   
   private static final String CAUSE_FIELD = "cause";

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
         Throwable cause = extract(original, filters);
         
         if(cause != null) {
            return new InternalError(value, cause);
         }
      }
      return new InternalError(value, original);
   }
   
   private Throwable extract(Throwable original, String... filters) {
      Throwable cause = extractor.extractCause(original);
      
      if(cause != null) {
         List<StackTraceElement> list = filter.filter(cause, filters);
         StackTraceElement[] trace = list.toArray(empty);
         Throwable child = cause.getCause();
         Throwable inner = extract(child, filters);
         
         if(trace.length > 0) {
            cause.setStackTrace(trace);
         }
         if(inner != child) {
            try {
               Field field = Throwable.class.getDeclaredField(CAUSE_FIELD);
               
               field.setAccessible(true);
               field.set(cause, inner);
            } catch(Exception e) {}
         }
      }
      return cause;
   }
}
