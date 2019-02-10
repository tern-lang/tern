package tern.core.function.dispatch;

import tern.core.constraint.Constraint;
import tern.core.error.ErrorHandler;
import tern.core.function.Connection;
import tern.core.function.resolve.FunctionCall;
import tern.core.function.resolve.FunctionConnection;
import tern.core.function.resolve.FunctionResolver;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.variable.Value;

public class TypeInstanceDispatcher implements FunctionDispatcher {
   
   private final FunctionResolver resolver;
   private final ErrorHandler handler;
   private final String name;    
   
   public TypeInstanceDispatcher(FunctionResolver resolver, ErrorHandler handler, String name) {
      this.resolver = resolver;
      this.handler = handler;
      this.name = name;
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint constraint, Type... arguments) throws Exception {
      Type object = constraint.getType(scope);
      FunctionCall call = resolver.resolveInstance(scope, object, name, arguments);
      
      if(call == null) {
         handler.failCompileInvocation(scope, object, name, arguments);
      }
      return call.check(scope, constraint, arguments);
   }
   
   @Override
   public Connection connect(Scope scope, Value value, Object... arguments) throws Exception {
      Object object = value.getValue();
      FunctionCall call = resolver.resolveInstance(scope, object, name, arguments);
      
      if(call == null) {
         handler.failRuntimeInvocation(scope, object, name, arguments);
      }
      return new FunctionConnection(call);
   }
}