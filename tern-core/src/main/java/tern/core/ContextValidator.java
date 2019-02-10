package tern.core;

import tern.core.module.Module;
import tern.core.type.Type;

public interface ContextValidator {
   void validate(Context context) throws Exception;
   void validate(Type type) throws Exception;
   void validate(Module module) throws Exception;
}