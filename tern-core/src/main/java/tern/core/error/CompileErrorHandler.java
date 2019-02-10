package tern.core.error;

import tern.core.result.Result;
import tern.core.scope.Scope;
import tern.core.stack.ThreadStack;
import tern.core.type.Type;
import tern.core.type.TypeExtractor;

public class CompileErrorHandler {

   private final CompileErrorFormatter formatter;
   private final InternalErrorBuilder builder;
   
   public CompileErrorHandler(TypeExtractor extractor, ThreadStack stack) {
      this(extractor, stack, true);
   }
   
   public CompileErrorHandler(TypeExtractor extractor, ThreadStack stack, boolean replace) {
      this.builder = new InternalErrorBuilder(stack, replace);
      this.formatter = new CompileErrorFormatter(extractor);
   }
   
   public Result handleAccessError(Scope scope, String name) {
      String message = formatter.formatAccessError(name);
      throw builder.createInternalException(message);
   }
   
   public Result handleAccessError(Scope scope, Type type, String name) {
      String message = formatter.formatAccessError(type, name);
      throw builder.createInternalException(message);
   }

   public Result handleAccessError(Scope scope, Type type, String name, Type[] list) {
      String message = formatter.formatAccessError(type, name, list);
      throw builder.createInternalException(message);
   }

   public Result handleGenericError(Scope scope, String name, Type[] list) {
      String message = formatter.formatGenericError(name, list);
      throw builder.createInternalException(message);
   }

   public Result handleReferenceError(Scope scope, String name) {
      String message = formatter.formatReferenceError(name);
      throw builder.createInternalException(message);
   }

   public Result handleReferenceError(Scope scope, Type type, String name) {
      String message = formatter.formatReferenceError(type, name);
      throw builder.createInternalException(message);
   }
   
   public Result handleInvokeError(Scope scope, String name, Type[] list) {
      String message = formatter.formatInvokeError(name, list);
      throw builder.createInternalException(message);
   }
   
   public Result handleInvokeError(Scope scope, Type type, String name, Type[] list) {
      String message = formatter.formatInvokeError(type, name, list);
      throw builder.createInternalException(message);
   }
   
   public Result handleCastError(Scope scope, Type require, Type actual) {
      String message = formatter.formatCastError(require, actual);
      throw builder.createInternalException(message);
   }   
   
   public Result handleConstructionError(Scope scope, Type type) {
      String message = formatter.formatConstructionError(type);
      throw builder.createInternalException(message);
   }
}