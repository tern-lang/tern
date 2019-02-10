package tern.core.function;

import tern.core.constraint.Constraint;

public class ParameterBuilder {
   
   private static final String[] PREFIX = {
   "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", 
   "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
   
   public ParameterBuilder() {
      super();
   }
   
   public Parameter create(Constraint type, String name, int index) {
      return new Parameter(name, type, index, false);
   }

   public Parameter create(Constraint type, String name, int index, boolean variable) {
      return new Parameter(name, type, index, variable);
   }

   public Parameter create(Constraint type, int index) {
      return create(type, index, false);
   }
   
   public Parameter create(Constraint type, int index, boolean variable) {
      String prefix = PREFIX[index % PREFIX.length];
      
      if(index > PREFIX.length) {
         prefix += index / PREFIX.length;
      }
      return new Parameter(prefix, type, index, false, variable);
   }
}