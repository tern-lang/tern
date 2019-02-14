package org.ternlang.core.variable;

import static org.ternlang.core.constraint.Constraint.NONE;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.InternalStateException;

public class Transient extends Value {
   
   private final Constraint constraint;
   private final Object object;
   
   public Transient(Object object) {
      this(object, NONE);
   }
   
   public Transient(Object object, Constraint constraint) {
      this.constraint = constraint;
      this.object = object;
   }
   
   @Override
   public Constraint getConstraint(){
      return constraint;
   }
   
   @Override
   public <T> T getValue(){
      return (T)object;
   }
   
   @Override
   public void setValue(Object value){
      throw new InternalStateException("Illegal modification of transient");
   } 
   
   @Override
   public String toString() {
      return String.valueOf(object);
   }
}