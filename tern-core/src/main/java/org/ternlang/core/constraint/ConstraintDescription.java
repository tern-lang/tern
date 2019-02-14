package org.ternlang.core.constraint;

import java.util.List;

import org.ternlang.core.Entity;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;

public class ConstraintDescription {
   
   private final Constraint constraint;
   private final Entity entity;
   
   public ConstraintDescription(Constraint constraint, Entity entity) {
      this.constraint = constraint;
      this.entity = entity;
   }
   
   public String getDescription(){
      return getDescription(constraint);
   }
   
   private String getDescription(Constraint constraint) {
      StringBuilder builder = new StringBuilder();
      
      if(constraint != null && entity != null) {
         Scope scope = entity.getScope();
         Type type = constraint.getType(scope);

         if(type != null) {
            List<Constraint> generics = constraint.getGenerics(scope);
            int length = generics.size();

            builder.append(type);

            if (length > 0) {
               builder.append("<");

               for (int i = 0; i < length; i++) {
                  Constraint generic = generics.get(i);
                  String entry = getDescription(generic);

                  if (i > 0) {
                     builder.append(", ");
                  }
                  builder.append(entry);
               }
               builder.append(">");
            }
            return builder.toString();
         }
      }
      return "?";
   }
   
   @Override
   public String toString() {
      return getDescription();
   }
}
