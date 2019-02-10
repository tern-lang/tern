package tern.compile.validate;

import tern.core.error.InternalException;

public class ValidateException extends InternalException {

   public ValidateException(String message) {
      super(message);
   }
   
   public ValidateException(String message, Throwable cause) {
      super(message, cause);
   }
}
