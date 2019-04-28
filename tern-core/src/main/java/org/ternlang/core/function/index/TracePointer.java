package org.ternlang.core.function.index;

import static org.ternlang.core.function.index.Retention.NEVER;

import org.ternlang.core.attribute.AttributeResult;
import org.ternlang.core.attribute.AttributeResultBinder;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.function.Function;
import org.ternlang.core.function.Invocation;
import org.ternlang.core.scope.Scope;

public class TracePointer implements FunctionPointer {
   
   private final TraceInvocationResolver resolver;
   private final AttributeResultBinder binder;
   private final Function function;
   
   public TracePointer(Function function) {
      this.resolver = new TraceInvocationResolver(function);
      this.binder = new AttributeResultBinder(function);
      this.function = function;
   }

   @Override
   public ReturnType getType(Scope scope) {
      AttributeResult result = binder.bind(scope);

      if(result == null) {
         throw new InternalStateException("No return type for '" + function + "'");
      }
      return new AttributeType(result, scope);
   }
   
   @Override
   public Invocation getInvocation() {
      Invocation invocation = resolver.resolve();
   
      if(invocation == null) {
         throw new InternalStateException("No implementation for '" + function + "'");
      }
      return invocation;
   }
   
   @Override
   public Function getFunction() {
      return function;
   }


   @Override
   public Retention getRetention() {
      return NEVER;
   }
   
   @Override
   public String toString() {
      return String.valueOf(function);
   }
}
