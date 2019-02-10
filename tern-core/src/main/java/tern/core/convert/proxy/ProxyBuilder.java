package tern.core.convert.proxy;

import tern.core.Context;
import tern.core.module.Module;
import tern.core.scope.instance.Instance;
import tern.core.type.Type;
import tern.core.function.Function;

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