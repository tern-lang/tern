package tern.core.function;

public enum Origin{
   SYSTEM("Function is system level"),
   ERROR("Function does not exist"),
   PLATFORM("Function is from the host platform"), // a java method
   DEFAULT("Function is from a type or script");
   
   public final String description;
   
   private Origin(String description){
      this.description = description;
   }
   
   public boolean isError() {
      return this == ERROR;
   }
   
   public boolean isPlatform() {
      return this == PLATFORM;
   }
   
   public boolean isSystem() {
      return this == SYSTEM;
   }
}
