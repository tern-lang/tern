package org.ternlang.core.type.index;

import java.lang.reflect.Array;
import java.lang.reflect.Method;

import org.ternlang.core.convert.proxy.ProxyWrapper;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.function.Invocation;
import org.ternlang.core.scope.Scope;

public class MethodInvocation implements Invocation<Object>{

   private final MethodCallBinder binder;
   private final ProxyWrapper wrapper;
   private final Method method;
   
   public MethodInvocation(ProxyWrapper wrapper, Invocation invocation, Method method) {
      this.binder = new MethodCallBinder(invocation, method);
      this.wrapper = wrapper;
      this.method = method;
   }
   
   @Override
   public Object invoke(Scope scope, Object left, Object... list) throws Exception {
      if(method.isVarArgs()) {
         Class[] types = method.getParameterTypes();
         int require = types.length;
         int actual = list.length;
         int start = require - 1;
         int remaining = actual - start;
         
         if(remaining >= 0) {
            Class type = types[require - 1];
            Class component = type.getComponentType();
            Object array = Array.newInstance(component, remaining);
            
            for(int i = 0; i < remaining; i++) {
               try {
                  Array.set(array, i, list[i + start]);
               } catch(Exception e){
                  throw new InternalStateException("Invalid argument at " + i + " for" + method, e);
               }
            }
            Object[] copy = new Object[require];
            
            if(require > list.length) {
               System.arraycopy(list, 0, copy, 0, list.length);
            } else {
               System.arraycopy(list, 0, copy, 0, require);
            }
            copy[start] = array;
            list = copy;
         }
      }
      MethodCall call = binder.bind(left);
      Object value = call.call(left, list);
      
      return wrapper.fromProxy(value);
   }
}