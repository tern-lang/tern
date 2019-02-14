package org.ternlang.core.stack;

import org.ternlang.core.error.InternalException;

public class StackOverflowException extends InternalException {

   public StackOverflowException(String message) {
      super(message);
   }
   
   public StackOverflowException(Throwable cause) {
      super(cause);
   }
   
   public StackOverflowException(String message, Throwable cause) {
      super(message, cause);
   }
}