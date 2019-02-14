package org.ternlang.core.convert.proxy;

import static org.ternlang.core.Reserved.METHOD_EQUALS;
import static org.ternlang.core.Reserved.METHOD_HASH_CODE;
import static org.ternlang.core.Reserved.METHOD_TO_STRING;

import java.lang.reflect.Method;

import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.function.Connection;
import org.ternlang.core.function.Function;
import org.ternlang.core.function.resolve.FunctionCall;
import org.ternlang.core.function.resolve.FunctionResolver;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.Transient;
import org.ternlang.core.variable.Value;

public class FunctionProxyHandler implements ProxyHandler { 
   
   private final ProxyArgumentExtractor extractor;
   private final FunctionResolver resolver;
   private final ProxyWrapper wrapper;
   private final Function function;
   private final Value value;
   
   public FunctionProxyHandler(ProxyWrapper wrapper, FunctionResolver resolver, Function function) {
      this.extractor = new ProxyArgumentExtractor(wrapper);
      this.value = new Transient(function);
      this.resolver = resolver;
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
      Connection call = resolve(proxy, name, arguments);
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
   
   private Connection resolve(Object proxy, String name, Object[] arguments) throws Throwable {
      Type source = function.getSource();

      if(source != null) {
         Scope scope = source.getScope();
         FunctionCall call = resolver.resolveInstance(scope, proxy, name, arguments); 
         
         if(call != null) {
            return new ProxyConnection(call, scope);
         }
      }
      FunctionCall call = resolver.resolveValue(value, arguments); // here arguments can be null!!!
      
      if(call != null) {
         return new ProxyConnection(call, null);
      }
      return null;
   }
   
   @Override
   public Function extract() {
      return function;
   }
   
   private static class ProxyConnection implements Connection {

      private final FunctionCall call;
      private final Scope outer;
      
      public ProxyConnection(FunctionCall call, Scope outer) {
         this.outer = outer;
         this.call = call;
      }
      
      @Override
      public boolean match(Scope scope, Object object, Object... arguments) throws Exception {
         return call.match(scope, object, arguments);
      }
      
      @Override
      public Object invoke(Scope scope, Object object, Object... arguments) throws Exception {
         if(outer != null) {
            return call.invoke(outer, object, arguments);
         }
         return call.invoke(scope, scope, arguments);
      }      
   }
}