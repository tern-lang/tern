package tern.core.variable;

import java.util.concurrent.atomic.AtomicReference;

import tern.core.constraint.Constraint;
import tern.core.error.InternalStateException;

public class Blank extends Value {
   
   private final AtomicReference<Object> reference;
   private final Constraint constraint;
   private final int modifiers;
   
   public Blank(Object value, Constraint constraint, int modifiers) {
      this.reference = new AtomicReference<Object>(value);
      this.constraint = constraint;
      this.modifiers = modifiers;
   }
   
   @Override
   public boolean isConstant() {
      return reference.get() != null;
   }
   
   @Override
   public boolean isProperty() {
      return modifiers != -1;
   }
   
   @Override
   public int getModifiers() {
      return modifiers;
   }
   
   @Override
   public Constraint getConstraint() {
      return constraint;
   }
   
   @Override
   public <T> T getValue() {
      return (T)reference.get();
   }
   
   @Override
   public void setValue(Object value){
      if(!reference.compareAndSet(null, value)) {
         throw new InternalStateException("Illegal modification of constant");
      }
   } 
   
   @Override
   public String toString() {
      return String.valueOf(reference);
   }
}