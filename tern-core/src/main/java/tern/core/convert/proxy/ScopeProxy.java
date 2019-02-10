package tern.core.convert.proxy;

import tern.core.scope.instance.Instance;

public class ScopeProxy {

   private ProxyBuilder builder;
   private Instance instance;
   private Object proxy;
   
   public ScopeProxy(Instance instance) {
      this.builder = new ProxyBuilder();
      this.instance = instance;
   }
   
   public Object getProxy() {
      if(proxy == null) {
         proxy = builder.create(instance);
      }
      return proxy;
   }
}