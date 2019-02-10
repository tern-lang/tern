package tern.tree.reference;

import java.util.List;

import tern.core.Entity;
import tern.core.Evaluation;
import tern.core.constraint.Constraint;
import tern.core.constraint.ConstraintDescription;
import tern.core.error.InternalStateException;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.variable.Value;

public abstract class ConstraintReference extends Evaluation {

   private volatile ConstraintValue value;
   
   protected ConstraintReference() {
      super();
   }

   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      if(value == null) {
         value = create(scope);
      }
      return value.constraint;
   }
   
   @Override
   public Value evaluate(Scope scope, Value left) throws Exception {
      if(value == null) {
         value = create(scope);
      }
      return value;
   }
   
   protected abstract ConstraintValue create(Scope scope) throws Exception;
   
   protected static class ConstraintValue extends Value {
      
      private final Constraint constraint;
      private final Value value;
      
      public ConstraintValue(Constraint constraint, Value value, Entity entity) {
         this.constraint = new ConstraintDefinition(constraint, entity);
         this.value = value;       
      }
      
      @Override
      public Constraint getConstraint() {
         return constraint;
      }
      
      @Override
      public <T> T getValue() {
         return value.getValue();
      }
      
      @Override
      public void setValue(Object value){
         throw new InternalStateException("Illegal modification of literal '" + value + "'");
      }    
      
      @Override
      public String toString() {
         return value.toString();
      }      
   }   

   protected static class ConstraintDefinition extends Constraint {
      
      private ConstraintDescription description;
      private List<Constraint> generics;
      private List<String> imports;
      private Constraint constraint;
      private Type type;
      private String name;
      
      public ConstraintDefinition(Constraint constraint, Entity entity) {
         this.description = new ConstraintDescription(constraint, entity);
         this.constraint = constraint;      
      }
      
      @Override
      public List<String> getImports(Scope scope) {
         if(imports == null) {
            imports = constraint.getImports(scope);
         }
         return imports;
      }
      
      @Override
      public List<Constraint> getGenerics(Scope scope) {
         if(generics == null) {
            generics = constraint.getGenerics(scope);
         }
         return generics;
      }
      
      @Override
      public Type getType(Scope scope) {
         if(type == null) {
            type = constraint.getType(scope);
         }
         return type;
      }
      
      @Override
      public String getName(Scope scope) {
         if(name == null) {
            name = constraint.getName(scope);
         }
         return name;
      }      

      @Override
      public String toString() {
         return description.toString();
      }      
   }
}
