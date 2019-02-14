package org.ternlang.core.function;

import org.ternlang.core.function.Invocation;
import org.ternlang.core.scope.Scope;

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