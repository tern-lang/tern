package tern.core.result;

public class ReturnResult extends Result {
   
   private final Object value;
   
   public ReturnResult() {
      this(null);
   }
   
   public ReturnResult(Object value) {
      this.value = value;
   }
   
   @Override
   public boolean isReturn(){
      return true;
   }

   @Override
   public <T> T getValue() {
      return (T)value;
   }
}
