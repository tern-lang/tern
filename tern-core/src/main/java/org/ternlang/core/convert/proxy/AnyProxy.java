package org.ternlang.core.convert.proxy;

import org.ternlang.core.Any;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.function.resolve.FunctionResolver;
import org.ternlang.core.scope.Scope;

public class AnyProxy implements Any {

   private final AnyProxyHandler handler;
   private final Scope scope;

   public AnyProxy(ProxyWrapper wrapper, FunctionResolver resolver, Scope scope) {
      this.handler = new AnyProxyHandler(wrapper, resolver);
      this.scope = scope;
   }

   @Override
   public boolean equals(Object value) {
      try {
         return handler.equals(scope, value);
      } catch(Exception e) {
         throw new InternalStateException("Error invoking equals", e);
      }
   }

   @Override
   public int hashCode() {
      try {
         return handler.hashCode(scope);
      } catch(Exception e) {
         throw new InternalStateException("Error invoking hashCode", e);
      }
   }

   @Override
   public String toString() {
      try {
         return handler.toString(scope);
      } catch(Exception e) {
         throw new InternalStateException("Error invoking toString", e);
      }
   }

   public Scope getScope() {
      return scope;
   }
}
