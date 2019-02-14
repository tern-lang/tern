package org.ternlang.core.type.index;

import java.lang.reflect.Method;

import org.ternlang.core.ModifierType;

public class MethodMatcher {

   private final ModifierConverter converter;
   
   public MethodMatcher() {
      this.converter = new ModifierConverter();
   }
   
   public Method match(Method[] methods, Class require, String name) throws Exception {
      MethodType[] types = MethodType.values();

      for(Method method : methods) {         
         int modifiers = converter.convert(method);
         
         if(!ModifierType.isStatic(modifiers) && ModifierType.isPublic(modifiers)) {
            for(MethodType type : types) {            
               if(type.isWrite(method)) {
                  Class[] parameters = method.getParameterTypes();
                  Class actual = parameters[0];
                  
                  if(actual == require) {
                     String property = type.getProperty(method);
      
                     if(property.equals(name)) {
                        return method;
                     }
                  }
               }
            }
         }
      }
      return null;
   }
}