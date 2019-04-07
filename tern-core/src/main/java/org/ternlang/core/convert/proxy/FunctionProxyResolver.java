package org.ternlang.core.convert.proxy;

import org.ternlang.core.function.Function;
import org.ternlang.core.function.Invocation;
import org.ternlang.core.function.resolve.FunctionCall;
import org.ternlang.core.function.resolve.FunctionResolver;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.Transient;
import org.ternlang.core.variable.Value;

public class FunctionProxyResolver {
   
   private final FunctionResolver resolver;
   private final Function function;
   private final Value value;
   
   public FunctionProxyResolver(FunctionResolver resolver, Function function) {
      this.value = new Transient(function);
      this.resolver = resolver;
      this.function = function;
   }
   
   public Invocation resolve(Object proxy, String name, Object[] arguments) throws Throwable {
      Type source = function.getSource();

      if(source != null) {
         Scope scope = source.getScope();
         FunctionCall call = resolver.resolveInstance(scope, proxy, name, arguments); 
         
         if(call != null) {
            return new DefaultInvocation(call, scope);
         }
      }
      return resolver.resolveValue(value, arguments); // here arguments can be null!!!
   }

   private static class DefaultInvocation implements Invocation {

      private final FunctionCall call;
      private final Scope outer;
      
      public DefaultInvocation(FunctionCall call, Scope outer) {
         this.outer = outer;
         this.call = call;
      }
      
      @Override
      public Object invoke(Scope scope, Object object, Object... arguments) throws Exception {
         return call.invoke(outer, object, arguments);
      }      
   }
}
