package org.ternlang.core.convert.proxy;

import static org.ternlang.core.Reserved.METHOD_EQUALS;
import static org.ternlang.core.Reserved.METHOD_HASH_CODE;
import static org.ternlang.core.Reserved.METHOD_TO_STRING;

import org.ternlang.core.Any;
import org.ternlang.core.function.resolve.FunctionCall;
import org.ternlang.core.function.resolve.FunctionResolver;
import org.ternlang.core.scope.Scope;

public class AnyProxyHandler implements Any {

   private final FunctionResolver resolver;
   private final ProxyWrapper wrapper;

   public AnyProxyHandler(ProxyWrapper wrapper, FunctionResolver resolver) {
      this.resolver = resolver;
      this.wrapper = wrapper;
   }

   public boolean equals(Scope scope, Object value) throws Exception {
      Object convert = wrapper.fromProxy(value);
      FunctionCall call = resolver.resolveInstance(scope, scope, METHOD_EQUALS, convert);
      Object result = call.invoke(scope, scope, convert);

      if(result != null) {
         return Boolean.TRUE.equals(result);
      }
      return false;
   }

   public int hashCode(Scope scope) throws Exception {
      FunctionCall call = resolver.resolveInstance(scope, scope, METHOD_HASH_CODE);
      Object result = call.invoke(scope, scope);

      if(result != null) {
         return (Integer)result;
      }
      return -1;
   }

   public String toString(Scope scope) throws Exception {
      FunctionCall call = resolver.resolveInstance(scope, scope, METHOD_TO_STRING);
      Object result = call.invoke(scope, scope);

      if(result != null) {
         return String.valueOf(result);
      }
      return null;
   }
}
