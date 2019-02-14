package org.ternlang.core.scope.index;

import static org.ternlang.core.constraint.Constraint.NONE;

import org.ternlang.core.constraint.Constraint;

public class LocalReference extends Local {

   private Constraint constraint;
   private Object value;
   private String name;
   
   public LocalReference(Object value, String name) {
      this(value, name, NONE);
   }
   
   public LocalReference(Object value, String name, Constraint constraint) {
      this.constraint = constraint; 
      this.value = value;
      this.name = name;
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
   public void setValue(Object value) {
      this.value = value;
   }
   
   @Override
   public String toString() {
      return String.format("%s: %s", name, value);
   }
}