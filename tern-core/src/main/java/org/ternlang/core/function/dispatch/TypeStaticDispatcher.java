package org.ternlang.core.function.dispatch;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.ErrorHandler;
import org.ternlang.core.function.ArgumentListCompiler;
import org.ternlang.core.function.Connection;
import org.ternlang.core.function.resolve.FunctionCall;
import org.ternlang.core.function.resolve.FunctionConnection;
import org.ternlang.core.function.resolve.FunctionResolver;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.Value;

public class TypeStaticDispatcher implements FunctionDispatcher {
   
   private final ArgumentListCompiler compiler;
   private final FunctionResolver resolver;
   private final ErrorHandler handler;
   private final String name;
   
   public TypeStaticDispatcher(FunctionResolver resolver, ErrorHandler handler, String name) {
      this.compiler = new ArgumentListCompiler();
      this.resolver = resolver;
      this.handler = handler;
      this.name = name;
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint constraint, Constraint... arguments) throws Exception {
      Type type = constraint.getType(scope);
      Type[] types = compiler.compile(scope, arguments);
      FunctionCall call = resolver.resolveStatic(scope, type, name, types);

      if(call == null) {
         handler.failCompileInvocation(scope, type, name, types);
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