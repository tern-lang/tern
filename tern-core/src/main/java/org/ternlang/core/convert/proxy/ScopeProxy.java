package org.ternlang.core.convert.proxy;

import org.ternlang.core.scope.instance.Instance;

public class ScopeProxy {

   private ProxyConverter converter;
   private Instance instance;
   private Object proxy;
   
   public ScopeProxy(Instance instance) {
      this.converter = new ProxyConverter();
      this.instance = instance;
   }
   
   public Object getProxy() {
      if(proxy == null) {
         proxy = converter.convert(instance);
      }
      return proxy;
   }
}