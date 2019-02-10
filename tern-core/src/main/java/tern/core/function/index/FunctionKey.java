package tern.core.function.index;

import tern.core.type.Type;

public class FunctionKey {      

   private Type[] types;
   private String function;
   private int hash;
   
   public FunctionKey(String function, Type[] types) {
      this.function = function;
      this.types = types;
   }
   
   @Override
   public boolean equals(Object key) {
      if(key instanceof FunctionKey) {
         return equals((FunctionKey)key);
      }
      return false;
   }
   
   public boolean equals(FunctionKey key) {
      if(key.types.length != types.length) {
         return false;
      }
      for(int i = 0; i < types.length; i++) {
         if(types[i] != key.types[i]) {
            return false;
         }         
      }
      return key.function.equals(function);
   }
   
   @Override
   public int hashCode() {
      if(hash == 0) {
         int value = function.hashCode();
         
         for(Type type : types) {
            int order = 1;
            
            if(type != null) {
               order = type.getOrder();
            }
            value = value *31 + order;
         }
         hash = value;
      }
      return hash;
   }
   
   @Override
   public String toString() {
      return function;
   }
}