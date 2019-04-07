package org.ternlang.core.convert.proxy;

import static org.ternlang.core.Reserved.METHOD_EQUALS;
import static org.ternlang.core.Reserved.METHOD_HASH_CODE;
import static org.ternlang.core.Reserved.METHOD_TO_STRING;

import java.lang.reflect.Method;

import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.function.Function;
import org.ternlang.core.function.Invocation;
import org.ternlang.core.function.resolve.FunctionResolver;

public class FunctionProxyHandler implements ProxyHandler { 
   
   private final ProxyArgumentExtractor extractor;
   private final FunctionProxyResolver resolver;
   private final ProxyWrapper wrapper;
   private final Function function;
   
   public FunctionProxyHandler(ProxyWrapper wrapper, FunctionResolver resolver, Function function) {
      this.resolver = new FunctionProxyResolver(resolver, function);
      this.extractor = new ProxyArgumentExtractor(wrapper);
      this.function = function;
      this.wrapper = wrapper;
   }
   
   @Override
   public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {
      Object[] convert = extractor.extract(arguments);
      String name = method.getName();
      int width = convert.length;
      
      if(name.equals(METHOD_HASH_CODE)) {
         if(width != 0) {
            throw new InternalStateException("Closure '"+ METHOD_HASH_CODE +"' does not accept " + width + " arguments");
         }
         return function.hashCode();
      }
      if(name.equals(METHOD_TO_STRING)) {
         if(width != 0) {
            throw new InternalStateException("Closure '"+METHOD_TO_STRING+"' does not accept " + width + " arguments");
         }
         return function.toString();
      }
      if(name.equals(METHOD_EQUALS)) {
         if(width != 1) {
            throw new InternalStateException("Closure '"+METHOD_EQUALS+"' does not accept " + width + " arguments");
         }
         return function.equals(convert[0]);
      }
      return invoke(proxy, name, convert);
   }
   
   private Object invoke(Object proxy, String name, Object[] arguments) throws Throwable {
      Invocation call = resolver.resolve(proxy, name, arguments);
      int width = arguments.length;
      
      if(call == null) {
         throw new InternalStateException("Closure not matched with " + width +" arguments");
      }
      Object data = call.invoke(null, proxy, arguments);
      
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