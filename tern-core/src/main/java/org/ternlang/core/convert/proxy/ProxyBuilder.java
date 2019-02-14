package org.ternlang.core.convert.proxy;

import org.ternlang.core.Context;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.instance.Instance;
import org.ternlang.core.type.Type;
import org.ternlang.core.function.Function;

public class ProxyBuilder {
   
   public ProxyBuilder() {
      super();
   }
   
   public Object create(Instance instance) {
      if(instance != null) {
         Module module = instance.getModule();
         Context context = module.getContext();
         ProxyWrapper wrapper = context.getWrapper();
         
         return wrapper.asProxy(instance);  
      }
      return instance;
   }
   
   public Object create(Function function) {
      Type source = function.getSource();
      
      if(source != null) {
         Module module = source.getModule();
         Context context = module.getContext();
         ProxyWrapper wrapper = context.getWrapper();
         
         return wrapper.asProxy(function);
      }
      return function;
   }
   
   public Object create(Function function, Class require) {
      Type source = function.getSource();
      
      if(source != null) {
         Module module = source.getModule();
         Context context = module.getContext();
         ProxyWrapper wrapper = context.getWrapper();
         
         return wrapper.asProxy(function, require); 
      }
      return function;
   }
}