package org.ternlang.core.error;

import static org.ternlang.core.Reserved.TYPE_CONSTRUCTOR;

import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeExtractor;

public class CompileErrorFormatter {
   
   private final ErrorMessageFormatter formatter;
   
   public CompileErrorFormatter(TypeExtractor extractor) {
      this.formatter = new ErrorMessageFormatter(extractor);
   }

   public String formatAccessError(String name) {
      StringBuilder builder = new StringBuilder();
      
      builder.append("Variable '");
      builder.append(name);
      builder.append("' is not accessible in scope");
      
      return builder.toString();
   }
   
   public String formatAccessError(Type type, String name) {
      StringBuilder builder = new StringBuilder();
      
      builder.append("Property '");
      builder.append(name);
      builder.append("' for '");
      builder.append(type);
      builder.append("' is not accessible");
      
      return builder.toString();
   }

   public String formatReferenceError(String name) {
      StringBuilder builder = new StringBuilder();

      builder.append("Property '");
      builder.append(name);
      builder.append("' not found in scope");

      return builder.toString();
   }
   
   public String formatReferenceError(Type type, String name) {
      StringBuilder builder = new StringBuilder();
      
      builder.append("Property '");
      builder.append(name);
      builder.append("' not found for '");
      builder.append(type);
      builder.append("'");
      
      return builder.toString();
   }
   
   public String formatAccessError(Type type, String name, Type[] list) {
      StringBuilder builder = new StringBuilder();
      
      if(name.equals(TYPE_CONSTRUCTOR)) {
         builder.append("Constructor '");
      } else {
         builder.append("Function '");
      }      
      String signature = formatter.formatFunction(type, name, list);
      
      builder.append(signature);
      builder.append("' for '");
      builder.append(type);
      builder.append("' is not accessible");
      
      return builder.toString();
   }

   public String formatGenericError(String name, Type[] list) {
      StringBuilder builder = new StringBuilder();

      if(name.equals(TYPE_CONSTRUCTOR)) {
         builder.append("Constructor '");
      } else {
         builder.append("Function '");
      }
      String signature = formatter.formatFunction(null, name, list);

      builder.append(signature);
      builder.append("' hidden by generics");

      return builder.toString();
   }

   public String formatInvokeError(String name, Type[] list) {
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
   
   public String formatInvokeError(Type type, String name, Type[] list) {
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
   
   public String formatCastError(Type require, Type actual) {
      StringBuilder builder = new StringBuilder();
      
      builder.append("Cast from '");
      builder.append(actual);
      builder.append("' to '");
      builder.append(require);
      builder.append("' is not possible");
      
      return builder.toString();
   }   
   
   public String formatConstructionError(Type type) {
      StringBuilder builder = new StringBuilder();
      
      builder.append("Type '");
      builder.append(type);
      builder.append("' is not a concrete class");
      
      return builder.toString();
   }
}