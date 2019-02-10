package tern.core.type.index;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicReference;

public class MethodHandleBinder {

   private final AtomicReference<MethodHandle> reference;
   private final MethodHandleBuilder builder;

   public MethodHandleBinder(Method method) {
      this.reference = new AtomicReference<MethodHandle>();
      this.builder = new MethodHandleBuilder(method);
   }
   
   public MethodHandle bind(Object left) throws Exception {
      MethodHandle handle = reference.get();
      
      if(handle == null) {
         handle = builder.create();
         reference.set(handle);
      }
      return handle.bindTo(left);
   }
}