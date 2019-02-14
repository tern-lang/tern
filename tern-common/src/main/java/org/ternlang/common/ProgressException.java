package org.ternlang.common;

public class ProgressException extends RuntimeException {

   public ProgressException(String message) {
      super(message);
   }
   
   public ProgressException(Throwable cause) {
      super(cause);
   }
   
   public ProgressException(String message, Throwable cause) {
      super(message, cause);
   }
}