package org.ternlang.core.type.index;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;

import org.ternlang.core.Context;
import org.ternlang.core.convert.proxy.ProxyWrapper;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;

public class DefaultMethodHandle {
   
   private final MethodHandleBinder binder;
   private final Method method;

   public DefaultMethodHandle(Method method) {
      this.binder = new MethodHandleBinder(method);
      this.method = method;
   }

   public Object invoke(Scope scope, Object left, Object... arguments) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      ProxyWrapper wrapper = context.getWrapper();
      Object object = wrapper.toProxy(left);
      MethodHandle handle = binder.bind(object);
      
      try {
         return handle.invokeWithArguments(arguments);
      } catch(Throwable e) {
         throw new InternalStateException("Error invoking default method " + method, e);
      }
   }
}