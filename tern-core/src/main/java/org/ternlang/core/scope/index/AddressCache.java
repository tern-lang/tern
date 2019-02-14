package org.ternlang.core.scope.index;

public class AddressCache {

   private static final Address[] CACHE = new Address[1024];

   static {
      for(int i = 0; i < CACHE.length; i++) {
         CACHE[i] = AddressType.LOCAL.getAddress(null, i);
      }
   }   
   
   public static Address getAddress(int index) {
      if(index < 0 || index >= CACHE.length) {
         return AddressType.LOCAL.getAddress(null, index);
      }
      return CACHE[index];      
   }
}
