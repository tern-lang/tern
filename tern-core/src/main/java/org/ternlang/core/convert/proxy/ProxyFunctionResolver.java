package org.ternlang.core.convert.proxy;

import java.lang.reflect.Method;

import org.ternlang.core.function.Function;
import org.ternlang.core.function.Invocation;
import org.ternlang.core.function.resolve.FunctionCall;
import org.ternlang.core.function.resolve.FunctionResolver;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;

public class ProxyFunctionResolver {
   
   private final FunctionResolver resolver;
   private final MethodMatcher matcher;
   private final Function function;

   public ProxyFunctionResolver(FunctionResolver resolver, MethodMatcher matcher, Function function) {
      this.function = function;
      this.resolver = resolver;
      this.matcher = matcher;
   }

   public Invocation resolve(Object proxy, Method method, Object[] arguments) throws Throwable {
      Invocation invocation = matcher.match(proxy, method, arguments);
      
      if(invocation == null) {
         Type source = function.getSource();
         
         if(source != null) {
            String name = method.getName();
            Scope scope = source.getScope();
            FunctionCall call = resolver.resolveInstance(scope, proxy, name, arguments); 
            
            if(call != null) {
               return new DefaultInvocation(call, scope);
            }
         }
      }
      return invocation;
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
