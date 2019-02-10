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

public class TypeStaticDispatcher implements FunctionDispatcher {
   
   private final FunctionResolver resolver;
   private final ErrorHandler handler;
   private final String name;
   
   public TypeStaticDispatcher(FunctionResolver resolver, ErrorHandler handler, String name) {
      this.resolver = resolver;
      this.handler = handler;
      this.name = name;
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint constraint, Type... arguments) throws Exception {
      Type type = constraint.getType(scope);
      FunctionCall call = resolver.resolveStatic(scope, type, name, arguments);

      if(call == null) {
         handler.failCompileInvocation(scope, type, name, arguments);
      }
      return call.check(scope, constraint, arguments);
   } 

   @Override
   public Connection connect(Scope scope, Value value, Object... arguments) throws Exception { 
      Type type = value.getValue();
      FunctionCall call = resolver.resolveStatic(scope, type, name, arguments);

      if(call == null) {
         FunctionCall instance = resolver.resolveInstance(scope, (Object)type, name, arguments); // find on the type
      
         if(instance == null) {
            handler.failRuntimeInvocation(scope, type, name, arguments);
         }
         return new FunctionConnection(instance);   
      }
      return new TypeStaticConnection(call); 
   } 
   
   private static class TypeStaticConnection implements Connection {

      private final FunctionCall call;
      
      public TypeStaticConnection(FunctionCall call) {
         this.call = call;
      }
      
      @Override
      public boolean match(Scope scope, Object object, Object... arguments) throws Exception {
         return call.match(scope, object, arguments);
      }
      
      @Override
      public Object invoke(Scope scope, Object object, Object... arguments) throws Exception {
         return call.invoke(scope, null, arguments);
      } 
   }
}