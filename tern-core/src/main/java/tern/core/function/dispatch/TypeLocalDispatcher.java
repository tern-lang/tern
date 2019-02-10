package tern.core.function.dispatch;

import tern.core.constraint.Constraint;
import tern.core.error.ErrorHandler;
import tern.core.function.Connection;
import tern.core.function.resolve.FunctionCall;
import tern.core.function.resolve.FunctionResolver;
import tern.core.module.Module;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.variable.Value;

public class TypeLocalDispatcher implements FunctionDispatcher {
   
   private final FunctionResolver resolver;
   private final ErrorHandler handler;
   private final String name;
   
   public TypeLocalDispatcher(FunctionResolver resolver, ErrorHandler handler, String name) {
      this.resolver = resolver;
      this.handler = handler;
      this.name = name;
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint constraint, Type... arguments) throws Exception {
      Type object = constraint.getType(scope);
      FunctionCall match = bind(scope, object, arguments);
      
      if(match == null) {
         Type type = scope.getType();
         
         if(type != null) {
            handler.failCompileInvocation(scope, type, name, arguments);
         } else {
            handler.failCompileInvocation(scope, name, arguments);
         }
      }
      return match.check(scope, constraint, arguments);
   }

   @Override
   public Connection connect(Scope scope, Value value, Object... arguments) throws Exception {
      Scope object = value.getValue();
      Connection call = bind(scope, object, arguments);
      
      if(call == null) {
         Type type = scope.getType();
         
         if(type != null) {
            handler.failRuntimeInvocation(scope, type, name, arguments);
         } else {
            handler.failRuntimeInvocation(scope, name, arguments);
         }
      }
      return call;     
   }
   
   private FunctionCall bind(Scope scope, Type object, Type... arguments) throws Exception {
      Type type = scope.getType();
      FunctionCall local = resolver.resolveInstance(scope, type, name, arguments);
      
      if(local == null) {
         Module module = scope.getModule();
         FunctionCall external = resolver.resolveModule(scope, module, name, arguments); // maybe closure should be first
         
         if(external != null) {
            return external;
         }
         FunctionCall closure = resolver.resolveScope(scope, name, arguments); // closure
         
         if(closure != null) {
            return closure;
         }
      }
      return local;  
   }
   
   private Connection bind(Scope scope, Scope object, Object... arguments) throws Exception {
      FunctionCall local = resolver.resolveInstance(scope, scope, name, arguments);
      
      if(local == null) {
         Module module = scope.getModule();
         FunctionCall external = resolver.resolveModule(scope, module, name, arguments); // maybe closure should be first
         
         if(external != null) {
            return new TypeLocalConnection(external, module);
         }
         FunctionCall closure = resolver.resolveScope(scope, name, arguments); // closure
         
         if(closure != null) {
            return new TypeLocalConnection(closure, null);
         }
         return null;
      }
      return new TypeLocalConnection(local, null);  
   }
   
   private static class TypeLocalConnection implements Connection {

      private final FunctionCall call;
      private final Module module;
      
      public TypeLocalConnection(FunctionCall call, Module module) {
         this.module = module;
         this.call = call;
      }
      
      @Override
      public boolean match(Scope scope, Object object, Object... arguments) throws Exception {
         return call.match(scope, object, arguments);
      }
      
      @Override
      public Object invoke(Scope scope, Object object, Object... arguments) throws Exception {
         if(module != null) {
            return call.invoke(scope, module, arguments);
         }
         return call.invoke(scope, scope, arguments);
      } 
   }
}