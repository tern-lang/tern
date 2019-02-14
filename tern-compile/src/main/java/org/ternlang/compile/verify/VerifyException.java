package org.ternlang.compile.verify;

import java.util.List;

import org.ternlang.core.error.InternalException;

public class VerifyException extends InternalException {
   
   private final List<VerifyError> errors;
   
   public VerifyException(String message, List<VerifyError> errors) {
      super(message);
      this.errors = errors;
   }
   
   public List<VerifyError> getErrors() {
      return errors;
   }
}