package org.ternlang.core.function.dispatch;

import static org.ternlang.core.constraint.Constraint.NONE;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.ErrorHandler;
import org.ternlang.core.function.Connection;
import org.ternlang.core.function.resolve.FunctionCall;
import org.ternlang.core.function.resolve.FunctionResolver;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.variable.Value;

public class ValueDispatcher implements FunctionDispatcher {
   
   private final FunctionResolver resolver;
   private final ErrorHandler handler;
   private final String name;
   
   public ValueDispatcher(FunctionResolver resolver, ErrorHandler handler, String name) {
      this.resolver = resolver;
      this.handler = handler;
      this.name = name;
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint value, Constraint... list) throws Exception {
      return NONE;
   }

   @Override
   public Connection connect(Scope scope, Value value, Object... list) throws Exception {
      Value reference = value.getValue();
      FunctionCall call = resolver.resolveValue(reference, list); // function variable
      
      if(call == null) {
         handler.failRuntimeInvocation(scope, name, list);
      }
      return new ValueConnection(call);  
   }
   
   private static class ValueConnection implements Connection {

      private final FunctionCall call;
      
      public ValueConnection(FunctionCall call) {
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