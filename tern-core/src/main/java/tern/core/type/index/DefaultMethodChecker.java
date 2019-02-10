package tern.core.type.index;

import java.lang.reflect.Method;

public class DefaultMethodChecker {
   
   private final DefaultMethodAccessor accessor;
   
   public DefaultMethodChecker() {
      this.accessor = new DefaultMethodAccessor();
   }
   
   public boolean check(Method method) throws Exception {
      Method access = accessor.access();

      if(access != null) {
        Object result = access.invoke(method);
        Boolean value = (Boolean)result;
        
        return value.booleanValue();
      }
      return false;
    }
}