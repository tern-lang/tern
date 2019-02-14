package org.ternlang.core.link;

public enum ImportType {
   IMPLICIT(false), // import foo.blah.*
   EXPLICIT(true); // import foo.blah.Bar
   
   public final boolean required;

   private ImportType(boolean required){
      this.required = required;
   }
   
   public boolean isRequired() {
      return required;
   }
}