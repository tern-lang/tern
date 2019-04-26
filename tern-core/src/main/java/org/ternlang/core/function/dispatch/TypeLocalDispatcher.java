package org.ternlang.core.function.dispatch;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.ErrorHandler;
import org.ternlang.core.function.ArgumentListCompiler;
import org.ternlang.core.function.Connection;
import org.ternlang.core.function.resolve.FunctionCall;
import org.ternlang.core.function.resolve.FunctionResolver;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.Value;

public class TypeLocalDispatcher implements FunctionDispatcher {
   
   private final ArgumentListCompiler compiler;
   private final FunctionResolver resolver;
   private final ErrorHandler handler;
   private final String name;
   
   public TypeLocalDispatcher(FunctionResolver resolver, ErrorHandler handler, String name) {
      this.compiler = new ArgumentListCompiler();
      this.resolver = resolver;
      this.handler = handler;
      this.name = name;
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint constraint, Constraint... arguments) throws Exception {
      Type object = constraint.getType(scope);
      Type[] types = compiler.compile(scope, arguments);
      FunctionCall match = bind(scope, object, types);
      
      if(match == null) {
         Type type = scope.getType();
         
         if(type != null) {
            handler.failCompileInvocation(scope, type, name, types);
         } else {
            handler.failCompileInvocation(scope, name, types);
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