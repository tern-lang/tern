package tern.core.convert;

import tern.core.Context;
import tern.core.convert.proxy.ProxyWrapper;
import tern.core.module.Module;
import tern.core.scope.Scope;

public class StringBuilder {

   public static String create(Scope scope, Object left) {
      Module module = scope.getModule();
      Context context = module.getContext();
      ProxyWrapper wrapper = context.getWrapper();
      Object object = wrapper.toProxy(left);
      
      return String.valueOf(object);
   }
   
}