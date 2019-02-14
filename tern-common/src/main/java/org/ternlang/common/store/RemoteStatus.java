package org.ternlang.common.store;

public enum RemoteStatus {
   SUCCESS,
   NOT_FOUND,
   ERROR;
   
   public boolean isNotFound(){
      return this == NOT_FOUND;
   }
   
   public boolean isSuccess() {
      return this == SUCCESS;
   }
   
   public boolean isError(){
      return this == ERROR;
   }
}