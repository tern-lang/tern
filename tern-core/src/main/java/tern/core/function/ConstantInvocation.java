package tern.core.function;

import tern.core.function.Invocation;
import tern.core.scope.Scope;

public class ConstantInvocation implements Invocation {

   private final Object value;
   
   public ConstantInvocation(Object value) {
      this.value = value;
   }
   
   @Override
   public Object invoke(Scope scope, Object object, Object... list) throws Exception {
      return value;
   }
}