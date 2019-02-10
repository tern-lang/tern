package tern.core.type;

public enum Category {
   STATIC, // static declarations
   INSTANCE, // instance declarations
   OTHER; // other
   
   public boolean isStatic(){
      return this == STATIC;
   }
   
   public boolean isInstance(){
      return this == INSTANCE;
   }
}
