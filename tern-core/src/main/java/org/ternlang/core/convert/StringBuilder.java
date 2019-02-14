package org.ternlang.core.convert;

import org.ternlang.core.Context;
import org.ternlang.core.convert.proxy.ProxyWrapper;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;

public class StringBuilder {

   public static String create(Scope scope, Object left) {
      Module module = scope.getModule();
      Context context = module.getContext();
      ProxyWrapper wrapper = context.getWrapper();
      Object object = wrapper.toProxy(left);
      
      return String.valueOf(object);
   }
   
}