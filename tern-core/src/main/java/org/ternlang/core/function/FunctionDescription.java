package org.ternlang.core.function;

import org.ternlang.core.module.Module;
import org.ternlang.core.type.Type;

public class FunctionDescription {

   private final Signature signature;
   private final Type source;
   private final String name;
   
   public FunctionDescription(Signature signature, Type source, String name) {
      this(signature, source, name, 0);
   }
  
   public FunctionDescription(Signature signature, Type source, String name, int start) {
      this.signature = signature;
      this.source = source;
      this.name = name;
   }
   
   public String getDescription() {
      StringBuilder builder = new StringBuilder();
      
      if(source != null) {
         String name = source.getName();
         Module module = source.getModule();
         String prefix = module.getName();
         
         builder.append(prefix);
         builder.append(".");
         
         if(name != null) {
            builder.append(name);
            builder.append(".");
         }
      } 
      builder.append(name);
      builder.append(signature);
      
      return builder.toString();
   }
   
   @Override
   public String toString() {
      return getDescription();
   }
}