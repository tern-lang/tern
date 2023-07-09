package org.ternlang.core.type.index;

import org.ternlang.core.convert.proxy.ProxyWrapper;
import org.ternlang.core.function.Invocation;
import org.ternlang.core.scope.Scope;

import java.lang.reflect.Method;

public class DefaultMethodInvocation implements Invocation<Object>{

   private final DefaultMethodHandle handle;
   private final Alignment alignment;
   private final Method method;
   
   public DefaultMethodInvocation(ProxyWrapper wrapper, Method method) {
      this.handle = new DefaultMethodHandle(wrapper, method);
      this.alignment = Alignment.resolve(method);
      this.method = method;
   }
   
   @Override
   public Object invoke(Scope scope, Object left, Object... list) throws Exception {
      Object[] arguments = alignment.align(method, list);
      return handle.invoke(scope, left, arguments);
   }
}