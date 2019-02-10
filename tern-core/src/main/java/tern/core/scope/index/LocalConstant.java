package tern.core.scope.index;

import static tern.core.constraint.Constraint.NONE;

import tern.core.constraint.Constraint;
import tern.core.error.InternalStateException;

public class LocalConstant extends Local {

   private final Constraint constraint;
   private final Object value;
   private final String name;
   
   public LocalConstant(Object value, String name) {
      this(value, name, NONE);
   }

   public LocalConstant(Object value, String name, Constraint constraint) {
      this.constraint = constraint;
      this.value = value;
      this.name = name;
   }
   
   @Override
   public boolean isConstant(){
      return true;
   }
   
   @Override
   public Constraint getConstraint() {
      return constraint;
   }
   
   @Override
   public String getName() {
      return name;
   }
   
   @Override
   public <T> T getValue() {
      return (T)value;
   }
   
   @Override
   public void setValue(Object value){
      throw new InternalStateException("Illegal modification of constant");
   } 
   
   @Override
   public String toString() {
      return String.format("%s: %s", name, value);
   }
}