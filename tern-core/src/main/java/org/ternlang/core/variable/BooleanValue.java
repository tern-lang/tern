package org.ternlang.core.variable;

import org.ternlang.core.error.InternalStateException;

public class BooleanValue extends Value {   
   
   public static final BooleanValue TRUE = new BooleanValue(true);
   public static final BooleanValue FALSE = new BooleanValue(false); 

   private final Boolean value;
   
   public BooleanValue(Boolean value) {
      this.value = value;
   }
   
   @Override
   public Class getType() {
      return Boolean.class;
   }     
   
   @Override
   public Boolean getValue(){
      return value;
   }
   
   @Override
   public void setValue(Object value){
      throw new InternalStateException("Illegal modification of value");
   }
   
   @Override
   public String toString() {
      return String.valueOf(value);
   }
}