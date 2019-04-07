package org.ternlang.core.error;

import static org.ternlang.core.Reserved.ANY_TYPE;
import static org.ternlang.core.Reserved.DEFAULT_MODULE;
import static org.ternlang.core.Reserved.TYPE_CONSTRUCTOR;

import org.ternlang.core.convert.TypeInspector;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeExtractor;

public class ErrorMessageFormatter {
   
   private final TypeExtractor extractor;
   private final TypeInspector inspector;

   public ErrorMessageFormatter(TypeExtractor extractor) {
      this.inspector = new TypeInspector();
      this.extractor = extractor;
   }
   
   public String formatFunction(Type type, String name, Object[] list) {
      if(name.equals(TYPE_CONSTRUCTOR)) {
         if (!inspector.isClass(type)) {
            return formatFunction(name, list, 1);
         }
      }
      return formatFunction(name, list, 0);
   }
   
   public String formatFunction(Type type, String name, Type[] list) {
      if(name.equals(TYPE_CONSTRUCTOR)) {
         if (!inspector.isClass(type)) {
            return formatFunction(name, list, 1);
         }
      }
      return formatFunction(name, list, 0);
   }

   private String formatFunction(String name, Type[] list, int start) {
      StringBuilder builder = new StringBuilder();
      
      builder.append(name);
      builder.append("(");

      for(int i = start; i < list.length; i++) {
         Type type = list[i];

         if(i > start) {
            builder.append(", ");
         }
         if(type != null) {
            builder.append(type);
         } else {
            builder.append(DEFAULT_MODULE);
            builder.append(".");
            builder.append(ANY_TYPE);
         }
      }
      builder.append(")");
      
      return builder.toString();      
   }
   
   private String formatFunction(String name, Object[] list, int start) {
      StringBuilder builder = new StringBuilder();
      
      builder.append(name);
      builder.append("(");

      for(int i = start; i < list.length; i++) {
         Object value = list[i];
         Type parameter = extractor.getType(value);
         
         if(i > start) {
            builder.append(", ");
         }
         if(parameter != null) {
            builder.append(parameter);
         } else {
            builder.append(DEFAULT_MODULE);
            builder.append(".");
            builder.append(ANY_TYPE);
         }
      }
      builder.append(")");
      
      return builder.toString();
   }
}
