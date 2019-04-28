package org.ternlang.core.convert.proxy;

import java.lang.reflect.Method;

import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.function.Function;
import org.ternlang.core.function.Invocation;
import org.ternlang.core.function.resolve.FunctionResolver;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;

public class FunctionProxyHandler implements ProxyHandler { 
   
   private final ProxyArgumentExtractor extractor;
   private final ProxyFunctionResolver resolver;
   private final ProxyWrapper wrapper;
   private final Function function;
   private final Module module;
   
   public FunctionProxyHandler(ProxyWrapper wrapper, FunctionResolver resolver, MethodMatcher matcher, Function function, Module module) {
      this.resolver = new ProxyFunctionResolver(resolver, matcher, function);
      this.extractor = new ProxyArgumentExtractor(wrapper);
      this.function = function;
      this.wrapper = wrapper;
      this.module = module;
   }
   
   @Override
   public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {
      Object[] convert = extractor.extract(arguments);
      Invocation invocation = resolver.resolve(proxy, method, convert);
      int width = convert.length;
      
      if(invocation == null) {
         throw new InternalStateException("Closure not matched with " + width +" arguments");
      }
      Scope scope = module.getScope();
      Object data = invocation.invoke(scope, proxy, convert);
      
      if(data != null) {
         return wrapper.toProxy(data);
      }
      return null;
   }

   @Override
   public Function extract() {
      return function;
   }
}