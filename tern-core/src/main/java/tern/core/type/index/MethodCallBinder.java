package tern.core.type.index;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import tern.core.Context;
import tern.core.error.InternalStateException;
import tern.core.function.Invocation;
import tern.core.module.Module;
import tern.core.platform.Platform;
import tern.core.platform.PlatformProvider;
import tern.core.scope.instance.Instance;
import tern.core.scope.instance.SuperInstance;
import tern.core.type.Type;

public class MethodCallBinder {
   
   private final MethodCall bridge;
   private final MethodCall object;
   private final MethodCall base;
   
   public MethodCallBinder(Invocation invocation, Method method) {
      this.object = new ObjectCall(invocation, method); // this.
      this.bridge = new BridgeCall(invocation); 
      this.base = new SuperCall(method); // super.
   }
   
   public MethodCall bind(Object target) {
      if(SuperInstance.class.isInstance(target)) {
         return base;
      }
      if(Instance.class.isInstance(target)) {
         return bridge;
      }
      return object;
   }
   
   private static class ObjectCall implements MethodCall {
      
      private final Invocation invocation;
      private final Method method;
      
      public ObjectCall(Invocation invocation, Method method) {
         this.invocation = invocation;
         this.method = method;
      }
      
      @Override
      public Object call(Object object, Object[] arguments) throws Exception {
         try {
            return invocation.invoke(null, object, arguments);
         }catch(InvocationTargetException cause) {
            Throwable target = cause.getTargetException();
            String name = method.getName();
            
            if(target != null) {
               throw new InternalStateException("Error occurred invoking '" + name + "'", target);
            }
            throw cause;
         }
      }
   }
   
   private static class BridgeCall implements MethodCall<Instance> {

      private final Invocation invocation;
      
      public BridgeCall(Invocation invocation) {
         this.invocation = invocation;
      }
      
      @Override
      public Object call(Instance instance, Object[] list) throws Exception {
         Object value = instance.getBridge();
         
         if(value == null) {
            throw new InternalStateException("No 'super' object could be found");
         }
         return invocation.invoke(instance, value, list);
      }
   }
   
   private static class SuperCall implements MethodCall<SuperInstance> {
      
      private final Method method;
      
      public SuperCall(Method method) {
         this.method = method;
      }

      @Override
      public Object call(SuperInstance instance, Object[] arguments) throws Exception {
         String name = method.getName();
         Type type = instance.getType();
         Object value = instance.getBridge();
         
         if(value == null) {
            throw new InternalStateException("No 'super' object could be found");
         }
         Module module = instance.getModule();
         Context context = module.getContext();
         PlatformProvider provider = context.getProvider();
         Platform platform = provider.create();

         if(platform == null) {
            throw new InternalStateException("No 'super' method for '" + name + "' found in '" + type + "'");
         }
         Invocation invocation = platform.createSuperMethod(type, method);
         Object result = invocation.invoke(instance, value, arguments);
         
         return result;
      }
   }
}