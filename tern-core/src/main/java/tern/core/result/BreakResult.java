package tern.core.result;

public class BreakResult extends Result {
   
   public BreakResult() {
      super();
   }
   
   @Override
   public boolean isBreak(){
      return true;
   }

   @Override
   public <T> T getValue() {
      return null;
   }
}
