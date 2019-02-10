package tern.core.function.dispatch;

import static tern.core.constraint.Constraint.NONE;

import tern.core.constraint.Constraint;
import tern.core.error.ErrorHandler;
import tern.core.function.Connection;
import tern.core.function.resolve.FunctionCall;
import tern.core.function.resolve.FunctionResolver;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.variable.Value;

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
   public Constraint compile(Scope scope, Constraint value, Type... list) throws Exception {
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