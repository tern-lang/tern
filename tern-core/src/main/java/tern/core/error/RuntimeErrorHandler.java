package tern.core.error;

import tern.core.module.Module;
import tern.core.result.Result;
import tern.core.scope.Scope;
import tern.core.stack.ThreadStack;
import tern.core.type.Type;
import tern.core.type.TypeExtractor;

public class RuntimeErrorHandler {

   private final RuntimeErrorFormatter formatter;
   private final InternalErrorBuilder builder;
   
   public RuntimeErrorHandler(TypeExtractor extractor, ThreadStack stack) {
      this(extractor, stack, true);
   }
   
   public RuntimeErrorHandler(TypeExtractor extractor, ThreadStack stack, boolean replace) {
      this.builder = new InternalErrorBuilder(stack, replace);
      this.formatter = new RuntimeErrorFormatter(extractor);
   }
   
   public Result handleReferenceError(Scope scope, String name) {
      String message = formatter.formatReferenceError(name);
      throw builder.createInternalException(message);
   }
   
   public Result handleReferenceError(Scope scope, Object object, String name) {
      String message = formatter.formatReferenceError(object, name);
      throw builder.createInternalException(message);
   }
   
   public Result handleInvokeError(Scope scope, String name, Object[] list) {
      String message = formatter.formatInvokeError(name, list);
      throw builder.createInternalException(message);
   }
   
   public Result handleInvokeError(Scope scope, Object value, String name, Object[] list) {
      String message = formatter.formatInvokeError(value, name, list);
      throw builder.createInternalException(message);
   }
   
   public Result handleInvokeError(Scope scope, Type type, String name, Object[] list) {
      String message = formatter.formatInvokeError(type, name, list);
      throw builder.createInternalException(message);
   }
   
   public Result handleInvokeError(Scope scope, Module module, String name, Object[] list) {
      String message = formatter.formatInvokeError(module, name, list);
      throw builder.createInternalException(message);
   }
}