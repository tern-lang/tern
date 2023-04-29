package org.ternlang.core.type.index;

import org.ternlang.core.function.Invocation;
import org.ternlang.core.scope.Scope;

import java.lang.reflect.Constructor;

public class ConstructorInvocation implements Invocation<Object> {

   private final Constructor constructor;
   private final Invocation invocation;
   private final Alignment alignment;

   public ConstructorInvocation(Invocation invocation, Constructor constructor) {
      this.alignment = Alignment.resolve(constructor);
      this.constructor = constructor;
      this.invocation = invocation;
   }

   @Override
   public Object invoke(Scope scope, Object left, Object... list) throws Exception {
      Object[] arguments = alignment.align(constructor, list);
      return invocation.invoke(scope, null, arguments);
   }
}