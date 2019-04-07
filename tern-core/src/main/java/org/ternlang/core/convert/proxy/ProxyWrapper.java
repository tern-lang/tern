package org.ternlang.core.convert.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import org.ternlang.core.Context;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.function.Function;
import org.ternlang.core.platform.Bridge;
import org.ternlang.core.scope.ScopeBinder;
import org.ternlang.core.scope.instance.Instance;

public class ProxyWrapper {

   private final ProxyInstanceBuilder builder;
   private final ScopeBinder binder;
   
   public ProxyWrapper(Context context) {
      this.builder = new ProxyInstanceBuilder(this, context);
      this.binder = new ScopeBinder();
   }
   
   public Object asProxy(Instance instance) {
      if(instance != null) {
         return builder.create(instance);
      }
      return null;
   }
   
   public Object asProxy(Function function) {
      if(function != null) {
         return builder.create(function, null);
      }
      return null;
   }
   
   public Object asProxy(Function function, Class require) {
      if(function != null) {
         return builder.create(function, require);
      }
      return null;
   }
   
   public Object toProxy(Object object) { 
      if(object != null) {
         if(Instance.class.isInstance(object)) {
            Instance instance = (Instance)object;
            Object proxy = instance.getProxy();
            
            return proxy;
         }
         if(Function.class.isInstance(object)) {
            Function function = (Function)object;
            Object proxy = function.getProxy();
            
            return proxy;
         }
      }
      return object;
   }

   public Object toProxy(Object object, Class require) { 
      if(object != null) {
         if(Instance.class.isInstance(object)) {
            Instance instance = (Instance)object;
            Object bridge = instance.getBridge();
               
            if(require.isInstance(bridge)) {
               return bridge;
            }
            Object proxy = instance.getProxy();
            
            if(!require.isInstance(proxy)) {
               throw new InternalStateException("Type does not extend or implement " + require);
            }
            return proxy;
         }
         if(Function.class.isInstance(object)) {
            Function function = (Function)object;
            Object proxy = function.getProxy(require);
            
            if(!require.isInstance(proxy)) {
               throw new InternalStateException("Type does not implement " + require);
            }
            return proxy;
         }
      }
      return object;
   }

   public Object fromProxy(Object object) {
      if(object != null) {
         if(Proxy.class.isInstance(object)) {
            InvocationHandler handler = Proxy.getInvocationHandler(object);
           
            if(ProxyHandler.class.isInstance(handler)) {
               ProxyHandler proxy = (ProxyHandler)handler;
               Object value = proxy.extract();
               
               return value;
            }
            return object;
         }
         if(AnyProxy.class.isInstance(object)) {
            AnyProxy proxy = (AnyProxy)object;
            Object value = proxy.getScope();

            return value;
         }
         if(Bridge.class.isInstance(object)) {
            Bridge bridge = (Bridge)object;
            Instance instance = bridge.getInstance();
            
            return binder.bind(instance, instance);
         }
      }
      return object;
   }
}