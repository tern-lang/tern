package org.ternlang.core.convert.proxy;

public class ProxyArgumentExtractor {

   private final ProxyWrapper wrapper;
   private final Object[] empty;
   
   public ProxyArgumentExtractor(ProxyWrapper wrapper) {
      this.empty = new Object[]{};
      this.wrapper = wrapper;
   }
   
   public Object[] extract(Object[] arguments) {
      if(arguments != null && arguments.length > 0) {
         for(int i = 0; i < arguments.length; i++) {
            Object argument = arguments[i];
            Object value = wrapper.fromProxy(argument);
            
            arguments[i] = value;
         }
         return arguments;
      }
      return empty;
   }
}