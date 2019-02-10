package tern.core.result;

public class ContinueResult extends Result {
   
   public ContinueResult() {
      super();
   }
   
   @Override
   public boolean isContinue(){
      return true;
   }

   @Override
   public <T> T getValue() {
      return null;
   }
}
