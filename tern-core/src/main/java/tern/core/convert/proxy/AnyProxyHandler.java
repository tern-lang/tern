package tern.core.convert.proxy;

import static tern.core.Reserved.METHOD_EQUALS;
import static tern.core.Reserved.METHOD_HASH_CODE;
import static tern.core.Reserved.METHOD_TO_STRING;

import tern.core.Any;
import tern.core.function.resolve.FunctionCall;
import tern.core.function.resolve.FunctionResolver;
import tern.core.scope.Scope;

public class AnyProxyHandler implements Any {

   private final ProxyArgumentExtractor extractor;
   private final FunctionResolver resolver;
   private final ProxyWrapper wrapper;

   public AnyProxyHandler(ProxyWrapper wrapper, FunctionResolver resolver) {
      this.extractor = new ProxyArgumentExtractor(wrapper);
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
