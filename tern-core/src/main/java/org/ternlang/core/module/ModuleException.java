package org.ternlang.core.module;

import org.ternlang.core.error.InternalArgumentException;

public class ModuleException extends InternalArgumentException {

   public ModuleException(String message) {
      super(message);
   }
   
   public ModuleException(String message, Throwable cause) {
      super(message, cause);
   }
}