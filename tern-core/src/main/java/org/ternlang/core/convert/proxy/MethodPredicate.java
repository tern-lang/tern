package org.ternlang.core.convert.proxy;

import java.lang.reflect.Method;

import org.ternlang.common.Predicate;

public class MethodPredicate implements Predicate<Method> {
   
   private final Class[] types;
   private final String name;
   
   public MethodPredicate(String name, Class... types) {
      this.types = types;
      this.name = name;
   }

   @Override
   public boolean accept(Method other) {
      String identifier = other.getName();
      
      if(identifier.equals(name)) {
         Class[] list = other.getParameterTypes();
      
         if(types.length == list.length) {
            for(int i = 0; i < list.length; i++) {
               Class require = types[i];
               Class actual = list[i];
               
               if(require != actual) {
                  return false;
               }
            }
            return true;
         }
      }
      return false;
   }
}
