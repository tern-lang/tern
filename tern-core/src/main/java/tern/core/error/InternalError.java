package tern.core.error;

public class InternalError extends Error {
   
   private final Throwable cause;
   private final Object original;
   
   public InternalError(Object original, Throwable cause) {
      this.original = original;
      this.cause = cause;
   }

   public Object getValue() {
      return original;
   }
   
   @Override
   public Throwable getCause() {
      return cause;
   }
   
   @Override
   public String toString() {
      return String.valueOf(original);
   }
}