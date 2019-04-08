package org.ternlang.core.convert.proxy;

import java.lang.reflect.Method;

import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.function.Function;
import org.ternlang.core.function.Invocation;
import org.ternlang.core.function.resolve.FunctionResolver;

public class FunctionProxyHandler implements ProxyHandler { 
   
   private final ProxyArgumentExtractor extractor;
   private final ProxyFunctionResolver resolver;
   private final ProxyWrapper wrapper;
   private final Function function;
   
   public FunctionProxyHandler(ProxyWrapper wrapper, FunctionResolver resolver, MethodMatcher matcher, Function function) {
      this.resolver = new ProxyFunctionResolver(resolver, matcher, function);
      this.extractor = new ProxyArgumentExtractor(wrapper);
      this.function = function;
      this.wrapper = wrapper;
   }
   
   @Override
   public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {
      Object[] convert = extractor.extract(arguments);
      Invocation match = resolver.resolve(proxy, method, convert);
      int width = convert.length;
      
      if(match == null) {
         throw new InternalStateException("Closure not matched with " + width +" arguments");
      }
      Object data = match.invoke(null, proxy, convert);
      
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