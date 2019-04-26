package org.ternlang.core.function.dispatch;

import static org.ternlang.core.constraint.Constraint.NONE;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.ErrorHandler;
import org.ternlang.core.function.Connection;
import org.ternlang.core.function.Function;
import org.ternlang.core.function.index.FunctionAdapter;
import org.ternlang.core.function.resolve.FunctionCall;
import org.ternlang.core.function.resolve.FunctionResolver;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.variable.Value;

public class ClosureDispatcher implements FunctionDispatcher {

   private final FunctionResolver resolver;
   private final ErrorHandler handler;
   private final String name;      
   
   public ClosureDispatcher(FunctionResolver resolver, ErrorHandler handler, String name) {
      this.handler = handler;
      this.resolver = resolver;
      this.name = name;
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint constraint, Constraint... arguments) throws Exception { 
      return NONE;
   }

   @Override
   public Connection connect(Scope scope, Value value, Object... arguments) throws Exception {
      Function function = value.getValue();
      Connection call = bind(scope, function, arguments); // this is not used often
      
      if(call == null) {
         handler.failRuntimeInvocation(scope, function, name, arguments);
      }
      return call;
   }
   
   private Connection bind(Scope scope, Function function, Object... arguments) throws Exception {
      FunctionCall call = resolver.resolveInstance(scope, function, name, arguments); // this is not used often
      
      if(call == null) {
         Object adapter = FunctionAdapter.wrap(function);
         FunctionCall instance = resolver.resolveInstance(scope, adapter, name, arguments);
         
         if(instance != null) {
            return new ClosureConnection(instance, true);
         }
         return null;
      }
      return new ClosureConnection(call, false);
   }
   
   private static class ClosureConnection implements Connection<Value> {
      
      private final FunctionCall call;
      private final boolean wrap;
      
      public ClosureConnection(FunctionCall call, boolean wrap) {
         this.wrap = wrap;
         this.call = call;
      }

      @Override
      public boolean match(Scope scope, Object object, Object... arguments) throws Exception {
         return call.match(scope, object, arguments);
      }
      
      @Override
      public Object invoke(Scope scope, Value value, Object... arguments) throws Exception {
         Function function = value.getValue();

         if(wrap) {
            FunctionAdapter adapter = FunctionAdapter.wrap(function);
            return call.invoke(scope, adapter, arguments);
         }
         return call.invoke(scope, function, arguments);
      }
   }
}