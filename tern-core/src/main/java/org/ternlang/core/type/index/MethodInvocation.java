package org.ternlang.core.type.index;

import org.ternlang.core.convert.proxy.ProxyWrapper;
import org.ternlang.core.function.Invocation;
import org.ternlang.core.scope.Scope;

import java.lang.reflect.Method;

public class MethodInvocation implements Invocation<Object>{

   private final MethodCallBinder binder;
   private final ProxyWrapper wrapper;
   private final Alignment alignment;
   private final Method method;
   
   public MethodInvocation(ProxyWrapper wrapper, Invocation invocation, Method method) {
      this.binder = new MethodCallBinder(invocation, method);
      this.alignment = Alignment.resolve(method);
      this.wrapper = wrapper;
      this.method = method;
   }
   
   @Override
   public Object invoke(Scope scope, Object left, Object... list) throws Exception {
      MethodCall call = binder.bind(left);
      Object[] arguments = alignment.align(method, list);
      Object value = call.call(left, arguments);
      
      return wrapper.fromProxy(value);
   }
}