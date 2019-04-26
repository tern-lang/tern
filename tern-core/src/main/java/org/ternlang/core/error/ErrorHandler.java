package org.ternlang.core.error;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.module.Module;
import org.ternlang.core.result.Result;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.stack.ThreadStack;
import org.ternlang.core.trace.Trace;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeExtractor;

public class ErrorHandler {

   private final ExternalErrorHandler external;
   private final InternalErrorHandler internal;
   private final RuntimeErrorHandler runtime;
   private final CompileErrorHandler compile;
   
   public ErrorHandler(TypeExtractor extractor, ThreadStack stack) {
      this(extractor, stack, true);
   }
   
   public ErrorHandler(TypeExtractor extractor, ThreadStack stack, boolean replace) {
      this.internal = new InternalErrorHandler(extractor, stack, replace);
      this.compile = new CompileErrorHandler(extractor, stack, replace);
      this.runtime = new RuntimeErrorHandler(extractor, stack, replace);
      this.external = new ExternalErrorHandler();
   }
   
   public Result failCompileConstruction(Scope scope, Type type) {
      return compile.handleConstructionError(scope, type);
   }

   public Result failCompileReference(Scope scope, String name) {
      return compile.handleReferenceError(scope, name);
   }

   public Result failCompileAccess(Scope scope, Type type, String name) {
      return compile.handleAccessError(scope, type, name);
   }

   public Result failCompileReference(Scope scope, Type type, String name) {
      return compile.handleReferenceError(scope, type, name); 
   }
   
   public Result failCompileCast(Scope scope, Type require, Type actual) {
      return compile.handleCastError(scope, require, actual); 
   }

   public Result failCompileGenerics(Scope scope, String name, Type[] list) {
      return compile.handleGenericError(scope, name, list);
   }

   public Result failCompileInvocation(Scope scope, String name, Type[] list) {
      return compile.handleInvokeError(scope, name, list);
   }

   public Result failCompileAccess(Scope scope, Type type, String name, Type[] list) {
      return compile.handleAccessError(scope, type, name, list);
   }

   public Result failCompileInvocation(Scope scope, Type type, String name, Type[] list) {
      return compile.handleInvokeError(scope, type, name, list); 
   }

   public Result failRuntimeReference(Scope scope, String name) {
      return runtime.handleReferenceError(scope, name); 
   }

   public Result failRuntimeReference(Scope scope, Object object, String name) {
      return runtime.handleReferenceError(scope, object, name); 
   }
   
   public Result failRuntimeInvocation(Scope scope, String name, Object[] list) {
      return runtime.handleInvokeError(scope, name, list); 
   }
   
   public Result failRuntimeInvocation(Scope scope, Object object, String name, Object[] list) {
      return runtime.handleInvokeError(scope, object, name, list); 
   }
   
   public Result failRuntimeInvocation(Scope scope, Type type, String name, Object[] list) {
      return runtime.handleInvokeError(scope, type, name, list); 
   }
   
   public Result failRuntimeInvocation(Scope scope, Module module, String name, Object[] list) {
      return runtime.handleInvokeError(scope, module, name, list); 
   }
   
   public Result failInternalError(Scope scope, Object cause) {
      if(InternalError.class.isInstance(cause)) {
         throw (InternalError)cause;
      }
      return internal.handleError(scope, cause); // fill in trace
   }
   
   public Result failInternalError(Scope scope, Throwable cause, Trace trace) {
      if(InternalError.class.isInstance(cause)) {
         throw (InternalError)cause;
      }
      return internal.handleError(scope, cause, trace); 
   }
   
   public Result failExternalError(Scope scope, Throwable cause) throws Exception {
      if(InternalError.class.isInstance(cause)) {
         InternalError error = (InternalError)cause;
         Object original = error.getValue();
         
         if(Exception.class.isInstance(original)) {
            throw (Exception)original; // throw original value
         }
         return external.handleError(scope, cause); // no stack trace
      }
      return external.handleError(scope, cause); // no stack trace
   }
}