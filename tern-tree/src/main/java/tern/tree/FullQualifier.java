package tern.tree;

import tern.parse.StringToken;

public class FullQualifier implements Qualifier {

   private final StringToken[] tokens;
   private final int count;

   public FullQualifier(StringToken... tokens) {
      this.count = tokens.length;
      this.tokens = tokens;
   }

   @Override
   public String getQualifier() {
      StringBuilder builder = new StringBuilder();

      for (int i = 0; i < count; i++) {
         String value = tokens[i].getValue();

         if (i > 0) {
            builder.append(".");
         }
         builder.append(value);
      }
      return builder.toString();
   }

   @Override
   public String getLocation() {
      StringBuilder builder = new StringBuilder();

      for (int i = 0; i < count - 1; i++) {
         String value = tokens[i].getValue();
         char first = value.charAt(0);

         if(first >='A' && first <='Z') {
            return builder.toString();
         }
         if (i > 0) {
            builder.append(".");
         }
         builder.append(value);
      }
      return builder.toString();
   }
   
   @Override
   public String getTarget() {
      StringBuilder builder = new StringBuilder();

      for (int i = 1; i < count; i++) {
         String value = tokens[i].getValue();
         char first = value.charAt(0);

         if(first >='A' && first <='Z') {
            builder.append(value);
            
            while(++i < count) {;
               value = tokens[i].getValue();
               first = value.charAt(0);
               
               if(first <'A' || first >'Z') {
                  return builder.toString();
               }
               builder.append("$");
               builder.append(value);
            }
         }
      }
      return builder.toString();
   }

   @Override
   public String getName() {
      if (count > 0) {
         StringToken token = tokens[count - 1];
         String value = token.getValue();

         return value;
      }
      return null;
   }
}