package org.ternlang.compile.validate;

import org.ternlang.core.error.InternalException;

public class ValidateException extends InternalException {

   public ValidateException(String message) {
      super(message);
   }
   
   public ValidateException(String message, Throwable cause) {
      super(message, cause);
   }
}
