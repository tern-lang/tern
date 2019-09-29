package org.ternlang.core.error;

import static org.ternlang.core.Reserved.IMPORT_JAVA_REFLECT;
import static org.ternlang.core.Reserved.IMPORT_JDK_INTERNAL;
import static org.ternlang.core.Reserved.IMPORT_SUN_REFLECT;
import static org.ternlang.core.Reserved.IMPORT_TERN;

import org.ternlang.core.stack.ThreadStack;

public class InternalErrorBuilder {
         
   private final InternalErrorFilter filter;
   private final ThreadStack stack;
   private final boolean replace;
   
   public InternalErrorBuilder(ThreadStack stack) {
      this(stack, true);
   }
   
   public InternalErrorBuilder(ThreadStack stack, boolean replace) {
      this.filter = new InternalErrorFilter(replace);
      this.replace = replace;
      this.stack = stack;
   }
   
   public InternalError createInternalError(Object value, Throwable original) {
      InternalError error = filter.filter(value, original, IMPORT_TERN, IMPORT_JAVA_REFLECT, IMPORT_JDK_INTERNAL, IMPORT_SUN_REFLECT);
      
      if(replace) {
         if(Throwable.class.isInstance(value)) {
            Throwable cause = (Throwable)value;
            StackTraceElement[] trace = stack.build(cause);
            
            if(trace.length > 0) {
               cause.setStackTrace(trace);
               error.setStackTrace(trace);
            }
         } else {
            StackTraceElement[] trace = stack.build();
            
            if(trace.length > 0) {
               error.setStackTrace(trace); // when there is no cause
            }
         }
      } else {
         if(Throwable.class.isInstance(value)) {
            Throwable cause = (Throwable)value;
            StackTraceElement[] trace = cause.getStackTrace();
            
            if(trace.length > 0) {
               error.setStackTrace(trace);
            }
         }
      }
      return error;
   }
   
   public InternalException createInternalException(Object value) {
      if(Throwable.class.isInstance(value)) {
         return new InternalStateException((Throwable)value);
      }
      return new InternalStateException(String.valueOf(value));
   }
}