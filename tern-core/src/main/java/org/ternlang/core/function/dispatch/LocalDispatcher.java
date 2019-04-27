package org.ternlang.core.function.dispatch;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.ErrorHandler;
import org.ternlang.core.function.ArgumentListCompiler;
import org.ternlang.core.function.Connection;
import org.ternlang.core.function.resolve.FunctionCall;
import org.ternlang.core.function.resolve.FunctionConnection;
import org.ternlang.core.function.resolve.FunctionResolver;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.Value;

public class LocalDispatcher implements FunctionDispatcher {
   
   private final ArgumentListCompiler compiler;
   private final FunctionResolver resolver;
   private final ErrorHandler handler;
   private final String name;  
   
   public LocalDispatcher(FunctionResolver resolver, ErrorHandler handler, String name) {
      this.compiler = new ArgumentListCompiler();
      this.resolver = resolver;
      this.handler = handler;
      this.name = name;
   }

   @Override
   public Constraint compile(Scope scope, Constraint constraint, Constraint... arguments) throws Exception {
      Type object = constraint.getType(scope);
      Type[] types = compiler.compile(scope, arguments);
      FunctionCall call = bind(scope, object, types);
      
      if(call == null) {
         handler.failCompileInvocation(scope, name, types);
      }
      return call.check(scope, constraint, arguments);
   }
   
   @Override
   public Connection connect(Scope scope, Value value, Object... arguments) throws Exception {
      Object object = value.getValue();
      FunctionCall call = bind(scope, object, arguments);
      
      if(call == null) {
         handler.failRuntimeInvocation(scope, name, arguments);
      }
      return new FunctionConnection(call);
   }
   
   private FunctionCall bind(Scope scope, Object object, Object... arguments) throws Exception {
      Module module = scope.getModule();
      FunctionCall local = resolver.resolveModule(scope, module, name, arguments);
      
      if(local == null) {
         return resolver.resolveScope(scope, name, arguments); // function variable
      }
      return local;  
   }
   
   private FunctionCall bind(Scope scope, Type object, Type... arguments) throws Exception {
      Module module = scope.getModule();
      FunctionCall local = resolver.resolveModule(scope, module, name, arguments);
      
      if(local == null) {
         return resolver.resolveScope(scope, name, arguments); // function variable
      }
      return local;  
   }
}