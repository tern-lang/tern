package org.ternlang.core.error;

public class InternalStateException extends InternalException {
   
   public InternalStateException(String message) {
      super(message);
   }
   
   public InternalStateException(Throwable cause) {
      super(cause);
   }
   
   public InternalStateException(String message, Throwable cause) {
      super(message, cause);
   }
}