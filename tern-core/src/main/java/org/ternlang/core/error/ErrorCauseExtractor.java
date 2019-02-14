package org.ternlang.core.error;

public class ErrorCauseExtractor {

   public ErrorCauseExtractor() {
      super();
   }

   public Object extract(Object cause) {
      if(InternalError.class.isInstance(cause)) {
         InternalError error = (InternalError)cause;
         return error.getValue();
      }
      return cause;
   }
}