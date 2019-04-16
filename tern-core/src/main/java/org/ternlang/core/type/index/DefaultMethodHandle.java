package org.ternlang.core.type.index;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;

import org.ternlang.core.convert.proxy.ProxyWrapper;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.scope.Scope;

public class DefaultMethodHandle {
   
   private final MethodHandleBinder binder;
   private final ProxyWrapper wrapper;
   private final Method method;

   public DefaultMethodHandle(ProxyWrapper wrapper, Method method) {
      this.binder = new MethodHandleBinder(method);
      this.wrapper = wrapper;
      this.method = method;
   }

   public Object invoke(Scope scope, Object left, Object... arguments) throws Exception {
      Object object = wrapper.toProxy(left);
      MethodHandle handle = binder.bind(object);
      
      try {
         Object result = handle.invokeWithArguments(arguments);
         
         if(result != null) {
            return wrapper.fromProxy(result);
         }
      } catch(Throwable e) {
         throw new InternalStateException("Error invoking default method " + method, e);
      }
      return null;
   }
}