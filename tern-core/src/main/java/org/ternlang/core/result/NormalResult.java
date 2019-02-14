package org.ternlang.core.result;

public class NormalResult extends Result {
   
   private final Object value;
   
   public NormalResult() {
      this(null);
   }
   
   public NormalResult(Object value) {
      this.value = value;
   }
   
   @Override
   public boolean isNormal(){
      return true;
   }

   @Override
   public <T> T getValue() {
      return (T)value;
   }
}
