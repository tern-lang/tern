package tern.core.type.index;

import static tern.core.Reserved.PROPERTY_GET;
import static tern.core.Reserved.PROPERTY_IS;
import static tern.core.Reserved.PROPERTY_SET;

import java.lang.reflect.Method;

public enum MethodType {
   GET(PROPERTY_GET),
   SET(PROPERTY_SET),      
   IS(PROPERTY_IS);

   private final String prefix;
   private final int size;

   private MethodType(String prefix) {
      this.size = prefix.length();         
      this.prefix = prefix;
   }
   
   public boolean isWrite(Method method) {
      String name = method.getName();
      int length = name.length();
      
      if(name.startsWith(prefix)) { // rubbish
         Class type = method.getReturnType();
         Class[] types = method.getParameterTypes();
         int count = types.length;

         if(type == void.class) {
            return length > size && count == 1;
         }
      }
      return false;
   }      

   public boolean isRead(Method method) {
      String name = method.getName();
      int length = name.length();
      
      if(name.startsWith(prefix)) {
         Class type = method.getReturnType();
         Class[] types = method.getParameterTypes();
         int count = types.length;

         if(type != void.class) {
            return length > size && count == 0;
         }
      }
      return false;
   }

   public String getProperty(Method method) {
      String name = method.getName();
      
      if(name.startsWith(prefix)) {
         return PropertyNameExtractor.getProperty(name, prefix);
      }
      return name;
   }
}