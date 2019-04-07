package org.ternlang.core.convert.proxy;

import static org.ternlang.core.Reserved.METHOD_EQUALS;
import static org.ternlang.core.Reserved.METHOD_HASH_CODE;
import static org.ternlang.core.Reserved.METHOD_TO_STRING;

import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.function.Function;
import org.ternlang.core.function.Invocation;
import org.ternlang.core.function.resolve.FunctionCall;
import org.ternlang.core.function.resolve.FunctionResolver;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.Transient;
import org.ternlang.core.variable.Value;

public class ProxyFunctionResolver {
   
   private final FunctionResolver resolver;
   private final Function function;
   private final Value value;
   
   public ProxyFunctionResolver(FunctionResolver resolver, Function function) {
      this.value = new Transient(function);
      this.resolver = resolver;
      this.function = function;
   }
   
   public Invocation resolve(Object proxy, String name, Object[] arguments) throws Throwable {
      Type source = function.getSource();
      
      if(name.equals(METHOD_HASH_CODE)) {
         return new HashCodeInvocation(function);
      }
      if(name.equals(METHOD_TO_STRING)) {
         return new ToStringInvocation(function);
      }
      if(name.equals(METHOD_EQUALS)) {
         return new EqualsInvocation(function);
      }
      if(source != null) {
         Scope scope = source.getScope();
         FunctionCall call = resolver.resolveInstance(scope, proxy, name, arguments); 
         
         if(call != null) {
            return new DefaultInvocation(call, scope);
         }
      }
      return resolver.resolveValue(value, arguments); // here arguments can be null!!!
   }

   private static class HashCodeInvocation implements Invocation {

      private final Function function;
      
      public HashCodeInvocation(Function function) {
         this.function = function;
      }
      
      @Override
      public Object invoke(Scope scope, Object object, Object... arguments) throws Exception {
         int width = arguments.length;
         
         if(width != 0) {
            throw new InternalStateException("Closure '" + METHOD_HASH_CODE + "' does not accept " + width + " arguments");
         }
         return function.hashCode();
      }      
   }
   
   private static class ToStringInvocation implements Invocation {

      private final Function function;
      
      public ToStringInvocation(Function function) {
         this.function = function;
      }
      
      @Override
      public Object invoke(Scope scope, Object object, Object... arguments) throws Exception {
         int width = arguments.length;
         
         if(width != 0) {
            throw new InternalStateException("Closure '" + METHOD_TO_STRING + "' does not accept " + width + " arguments");
         }
         return function.toString();
      }      
   }
   
   private static class EqualsInvocation implements Invocation {

      private final Function function;
      
      public EqualsInvocation(Function function) {
         this.function = function;
      }
      
      @Override
      public Object invoke(Scope scope, Object object, Object... arguments) throws Exception {
         int width = arguments.length;
         
         if(width != 1) {
            throw new InternalStateException("Closure '" + METHOD_EQUALS + "' does not accept " + width + " arguments");
         }
         return function.equals(arguments[0]);
      }      
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
