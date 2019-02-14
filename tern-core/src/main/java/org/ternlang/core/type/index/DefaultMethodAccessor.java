package org.ternlang.core.type.index;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicBoolean;

public class DefaultMethodAccessor {

   private static final String DEFAULT_METHOD = "isDefault";

   private AtomicBoolean check;
   private Method access;
   
   public DefaultMethodAccessor() {
      this.check = new AtomicBoolean(true);
   }
   
   public Method access() throws Exception {
      if(check.compareAndSet(true, false)) {
         try {
            access = Method.class.getDeclaredMethod(DEFAULT_METHOD);
         } catch(Throwable e) {
            return null;
         }
      }
      return access;
    }
}