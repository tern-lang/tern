package org.ternlang.core.convert.proxy;

import static org.ternlang.core.Reserved.METHOD_EQUALS;
import static org.ternlang.core.Reserved.METHOD_HASH_CODE;
import static org.ternlang.core.Reserved.METHOD_TO_STRING;

import java.lang.reflect.Method;

import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.function.Function;
import org.ternlang.core.function.Invocation;
import org.ternlang.core.scope.Scope;

public class ObjectMethodMatcher implements MethodMatcher {
   
   private final Function function;
   
   public ObjectMethodMatcher(Function function) {
      this.function = function;
   }

   @Override
   public Invocation match(Object proxy, Method method, Object[] arguments) throws Throwable {
      String name = method.getName();

      if(arguments.length == 0) {
         if(name.equals(METHOD_HASH_CODE)) {
            return new HashCodeInvocation(function);
         }
         if(name.equals(METHOD_TO_STRING)) {
            return new ToStringInvocation(function);
         }
      }
      if(arguments.length == 1) {
         if(name.equals(METHOD_EQUALS)) {
            return new EqualsInvocation(function);
         }
      }
      return null;
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
}
