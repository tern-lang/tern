package org.ternlang.core.variable;

import static org.ternlang.core.constraint.Constraint.NONE;

import org.ternlang.core.constraint.Constraint;

public class Reference extends Value {
   
   private Constraint constraint;
   private Object value;
   private int modifiers;
   
   public Reference(Object value) {
      this(value, NONE);
   }
   
   public Reference(Object value, Constraint constraint) {
      this(value, constraint, -1);
   }
   
   public Reference(Object value, Constraint constraint, int modifiers) {
      this.constraint = constraint; 
      this.modifiers = modifiers;
      this.value = value;
   }
   
   @Override
   public boolean isProperty(){
      return modifiers != -1;
   }
   
   @Override
   public int getModifiers(){
      return modifiers;
   }
   
   @Override
   public Constraint getConstraint() {
      return constraint;
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
      return String.valueOf(value);
   }
}