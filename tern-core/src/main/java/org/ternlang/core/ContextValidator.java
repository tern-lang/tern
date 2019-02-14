package org.ternlang.core;

import org.ternlang.core.module.Module;
import org.ternlang.core.type.Type;

public interface ContextValidator {
   void validate(Context context) throws Exception;
   void validate(Type type) throws Exception;
   void validate(Module module) throws Exception;
}