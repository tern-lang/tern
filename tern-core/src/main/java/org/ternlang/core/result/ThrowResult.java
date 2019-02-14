package org.ternlang.core.result;

public class ThrowResult extends Result {
   
   private final Object value;
   
   public ThrowResult(Object value) {
      this.value = value;
   }   
   
   @Override
   public boolean isThrow(){
      return true;
   }

   @Override
   public <T> T getValue() {
      return (T)value;
   }
}
