package org.ternlang.core.convert.proxy;

import org.ternlang.core.function.Function;

public class FunctionProxy {
   
   private ProxyConverter converter;
   private Function function;
   private Object proxy;
   
   public FunctionProxy(Function function) {
      this.converter = new ProxyConverter();
      this.function = function;
   }
   
   public Object getProxy() {
      if(proxy == null) {
         proxy = converter.convert(function);
      }
      return proxy;
   } 
   
   public Object getProxy(Class require) {
      return converter.convert(function, require);
   } 
}