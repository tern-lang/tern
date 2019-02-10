package tern.compile.verify;

import tern.core.module.Path;
import tern.core.trace.Trace;

public class VerifyError {

   private final Exception cause;
   private final Trace trace;
   
   public VerifyError(Exception cause, Trace trace) {
      this.cause = cause;
      this.trace = trace;
   }
   
   public String getDescription(){
      StringBuilder builder = new StringBuilder();
      
      if(cause != null) {
         String message = cause.getMessage();
         Path path = trace.getPath();
         int line = trace.getLine();
         
         builder.append(message);
         builder.append(" in ");
         builder.append(path);
         builder.append(" at line ");
         builder.append(line);
      }
      return builder.toString();
   }
   
   public Exception getCause(){
      return cause;
   }
   
   public Trace getTrace() {
      return trace;
   }
   
   @Override
   public String toString() {
      return getDescription();
   }
}
