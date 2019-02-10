package tern.core.type.index;

import java.lang.reflect.Constructor;

import tern.core.error.InternalStateException;

public class PrimitiveFunctionAccessor {

   public PrimitiveFunctionAccessor() {
      super();
   }

   public <T> T create(Class type) {
      try {
         Constructor constructor = type.getDeclaredConstructor();
         
         if(!constructor.isAccessible()) {
            constructor.setAccessible(true);
         }
         return (T)constructor.newInstance();
      } catch(Exception e) {
         throw new InternalStateException("Error creating " + type, e);
      }
   }
}