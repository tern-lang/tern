package org.ternlang.core.convert.proxy;

import java.lang.reflect.Method;

import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.function.resolve.FunctionCall;
import org.ternlang.core.function.resolve.FunctionResolver;
import org.ternlang.core.scope.Scope;

public class ScopeProxyHandler implements ProxyHandler {
   
   private final ProxyArgumentExtractor extractor;
   private final FunctionResolver resolver;
   private final ProxyWrapper wrapper;
   private final Scope scope;
   
   public ScopeProxyHandler(ProxyWrapper wrapper, FunctionResolver resolver, Scope scope) {
      this.extractor = new ProxyArgumentExtractor(wrapper);
      this.resolver = resolver;
      this.wrapper = wrapper;
      this.scope = scope;
   }
   
   @Override
   public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {
      String name = method.getName();
      Object[] convert = extractor.extract(arguments);
      FunctionCall call = resolver.resolveInstance(scope, scope, name, convert); // here arguments can be null!!!

      if(call == null) {
         throw new InternalStateException("Method '" + name + "' not found");
      }
      Object result = call.invoke(scope, scope, convert);

      if(result != null) {
         return wrapper.toProxy(result);
      }
      return null;
   }
   
   @Override
   public Scope extract() {
      return scope;
   }   
}