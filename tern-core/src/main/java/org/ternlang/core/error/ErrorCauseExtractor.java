package org.ternlang.core.error;

public class ErrorCauseExtractor {

   public ErrorCauseExtractor() {
      super();
   }

   public Object extractValue(Object cause) {
      if(cause != null) {
         if(InternalError.class.isInstance(cause)) {
            InternalError error = (InternalError)cause;
            return error.getValue();
         }
      }
      return cause;
   }
   
   public Throwable extractCause(Object cause) {
      if(cause != null) {
         if(Throwable.class.isInstance(cause)) {
            if(InternalError.class.isInstance(cause)) {
               InternalError error = (InternalError)cause;
               Throwable origin = error.getCause();
               
               if(cause != null) {
                  return extractCause(origin);
               }
            } else if(ReflectiveOperationException.class.isInstance(cause)) {
               Throwable error = (Throwable)cause;
               Throwable origin = error.getCause();
               
               if(cause != null) {
                  return extractCause(origin);
               }
            }
            return (Throwable)cause;
         }
      }
      return null;
   }
}