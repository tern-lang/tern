package org.ternlang.core.type;

import org.ternlang.core.module.Module;
import org.ternlang.core.type.Type;

public class TypeDescription {

   private final Type type;
   
   public TypeDescription(Type type) {
      this.type = type;
   }
   
   public String getDescription(){
      StringBuilder builder = new StringBuilder();
      
      if(type != null) {
         String name = type.getName();
         Module module = type.getModule();
         
         builder.append(module);
         
         if(name != null) {
            builder.append(".");
            builder.append(name);
         }               
      }
      return builder.toString();
   }
   
   @Override
   public String toString() {
      return getDescription();
   }
}