package org.ternlang.core.error;

import org.ternlang.core.module.Path;
import org.ternlang.core.trace.Trace;

public class InternalErrorFormatter {
   
   public InternalErrorFormatter() {
      super();
   }

   public Throwable formatInternalError(Throwable cause, Trace trace) {
      StringBuilder builder = new StringBuilder();
      
      if(trace != null) {
         String message = cause.getMessage();
         Path path = trace.getPath();
         int line = trace.getLine();
         
         builder.append(message);
         builder.append(" in ");
         builder.append(path);
         builder.append(" at line ");
         builder.append(line);

         String details = builder.toString();
         
         return new InternalStateException(details, cause);
      }
      return cause;
   }
}