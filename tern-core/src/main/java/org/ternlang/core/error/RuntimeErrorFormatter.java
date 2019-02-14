package org.ternlang.core.error;

import static org.ternlang.core.Reserved.TYPE_CONSTRUCTOR;

import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeExtractor;

public class RuntimeErrorFormatter {
   
   private final ErrorMessageFormatter formatter;
   private final TypeExtractor extractor;
   
   public RuntimeErrorFormatter(TypeExtractor extractor) {
      this.formatter = new ErrorMessageFormatter(extractor);
      this.extractor = extractor;
   }
   
   public String formatReferenceError(String name) {
      StringBuilder builder = new StringBuilder();
      
      builder.append("Variable '");
      builder.append(name);
      builder.append("' not found in scope");
      
      return builder.toString();
   }
   
   public String formatReferenceError(Object object, String name) {
      StringBuilder builder = new StringBuilder();

      builder.append("Property '");
      builder.append(name);
      builder.append("' not found");

      if(object != null) {
         Type type = extractor.getType(object);

         builder.append(" for '");
         builder.append(type);
         builder.append("'");
      }
      return builder.toString();
   }
   
   public String formatInvokeError(String name, Object[] list) {
      StringBuilder builder = new StringBuilder();
      
      if(name.equals(TYPE_CONSTRUCTOR)) {
         builder.append("Constructor '");
      } else {
         builder.append("Function '");
      }  
      String signature = formatter.formatFunction(null, name, list);
      
      builder.append(signature);
      builder.append("' not found in scope");
      
      return builder.toString();
   }
   
   public String formatInvokeError(Object object, String name, Object[] list) {
      StringBuilder builder = new StringBuilder();
      
      if(name.equals(TYPE_CONSTRUCTOR)) {
         builder.append("Constructor '");
      } else {
         builder.append("Function '");
      }  
      String signature = formatter.formatFunction(null, name, list);
      
      builder.append(signature);
      builder.append("' not found");
      
      if(object != null) {
         Type type = extractor.getType(object);
         
         builder.append(" for '");
         builder.append(type);
         builder.append("'");
      }
      return builder.toString();
   }
   
   public String formatInvokeError(Type type, String name, Object[] list) {
      StringBuilder builder = new StringBuilder();
      
      if(name.equals(TYPE_CONSTRUCTOR)) {
         builder.append("Constructor '");
      } else {
         builder.append("Function '");
      }  
      String signature = formatter.formatFunction(type, name, list);
      
      builder.append(signature);
      builder.append("' not found for '");
      builder.append(type);
      builder.append("'");
      
      return builder.toString();
   }
}