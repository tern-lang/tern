package org.ternlang.core.convert.proxy;

import org.ternlang.core.function.Function;

public class FunctionProxy {
   
   private ProxyBuilder builder;
   private Function function;
   private Object proxy;
   
   public FunctionProxy(Function function) {
      this.builder = new ProxyBuilder();
      this.function = function;
   }
   
   public Object getProxy() {
      if(proxy == null) {
         proxy = builder.create(function);
      }
      return proxy;
   } 
   
   public Object getProxy(Class require) {
      return builder.create(function, require);
   } 
}