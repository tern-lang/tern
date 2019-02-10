package tern.core.function;

import java.util.List;

import tern.core.Entity;
import tern.core.constraint.Constraint;
import tern.core.scope.Scope;
import tern.core.type.Type;

public class SignatureDescription {

   private final Signature signature;
   private final Entity entity;
   private final int start;
   
   public SignatureDescription(Signature signature, Entity entity) {
      this(signature, entity, 0);
   }
  
   public SignatureDescription(Signature signature, Entity entity, int start) {
      this.signature = signature;
      this.entity = entity;
      this.start = start;
   }

   public String getDescription() {
      StringBuilder builder = new StringBuilder();

      builder.append("(");
      
      if(signature != null) {
         List<Parameter> parameters = signature.getParameters();
         Scope scope = entity.getScope();
         int size = parameters.size();
         
         for(int i = start; i < size; i++) {
            Parameter parameter = parameters.get(i);
            Constraint constraint = parameter.getConstraint();
            Type type = constraint.getType(scope);
            String name = parameter.getName();
            
            if(i > start) {
               builder.append(", ");
            }
            builder.append(name);
            
            if(parameter.isVariable()) {
               builder.append("...");
            }
            if(type != null) {
               String qualifier = type.getName();
               
               builder.append(": ");
               builder.append(qualifier);
            }
         }
      }
      builder.append(")");
      return builder.toString();
   }
   
   @Override
   public String toString() {
      return getDescription();
   }
}