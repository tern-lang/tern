package tern.core.function.index;

import static tern.core.function.index.Retention.NEVER;

import tern.core.attribute.AttributeResult;
import tern.core.attribute.AttributeResultBinder;
import tern.core.error.InternalStateException;
import tern.core.function.ArgumentConverter;
import tern.core.function.Function;
import tern.core.function.Invocation;
import tern.core.function.Origin;
import tern.core.function.Signature;
import tern.core.scope.Scope;
import tern.core.stack.ThreadStack;

public class TracePointer implements FunctionPointer {
   
   private final AttributeResultBinder binder;
   private final Invocation invocation;
   private final Function function;
   
   public TracePointer(Function function, ThreadStack stack) {
      this.binder = new AttributeResultBinder(function);
      this.invocation = new TraceInvocation(function, stack);
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
   public Function getFunction() {
      return function;
   }
   
   @Override
   public Invocation getInvocation() {
      return invocation;
   }

   @Override
   public Retention getRetention() {
      return NEVER;
   }
   
   @Override
   public String toString() {
      return String.valueOf(function);
   }
   
   private static class TraceInvocation implements Invocation {
   
      private final ThreadStack stack;
      private final Function function;
      
      public TraceInvocation(Function function, ThreadStack stack) {
         this.function = function;
         this.stack = stack;
      }
      
      @Override
      public Object invoke(Scope scope, Object object, Object... arguments) throws Exception{
         Signature signature = function.getSignature();
         ArgumentConverter converter = signature.getConverter();
         Invocation invocation = function.getInvocation();
         Origin origin = signature.getOrigin();
         
         try {
            Object[] list = arguments;
            
            if(!origin.isSystem()) {
               stack.before(function);
            }
            if(origin.isPlatform()) {
               list = converter.convert(arguments);
            } else {
               list = converter.assign(arguments);
            }
            return invocation.invoke(scope, object, list);
         } finally {
            if(!origin.isSystem()) {
               stack.after(function);
            }
         }
      }
   }
}
