package tern.core.function.dispatch;

import tern.core.constraint.Constraint;
import tern.core.error.ErrorHandler;
import tern.core.function.Connection;
import tern.core.function.resolve.FunctionCall;
import tern.core.function.resolve.FunctionConnection;
import tern.core.function.resolve.FunctionResolver;
import tern.core.module.Module;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.variable.Value;

public class LocalDispatcher implements FunctionDispatcher {
   
   private final FunctionResolver binder;
   private final ErrorHandler handler;
   private final String name;  
   
   public LocalDispatcher(FunctionResolver binder, ErrorHandler handler, String name) {
      this.handler = handler;
      this.binder = binder;
      this.name = name;
   }

   @Override
   public Constraint compile(Scope scope, Constraint constraint, Type... arguments) throws Exception {
      Type object = constraint.getType(scope);
      FunctionCall call = bind(scope, object, arguments);
      
      if(call == null) {
         handler.failCompileInvocation(scope, name, arguments);
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
      FunctionCall local = binder.resolveModule(scope, module, name, arguments);
      
      if(local == null) {
         return binder.resolveScope(scope, name, arguments); // function variable
      }
      return local;  
   }
   
   private FunctionCall bind(Scope scope, Type object, Type... arguments) throws Exception {
      Module module = scope.getModule();
      FunctionCall local = binder.resolveModule(scope, module, name, arguments);
      
      if(local == null) {
         return binder.resolveScope(scope, name, arguments); // function variable
      }
      return local;  
   }
}