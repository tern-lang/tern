package org.ternlang.tree;

import org.ternlang.core.type.Type;

public class Operation {
   
   private final String name;
   private final Type type;
   
   public Operation(Type type, String name) {
      this.name = name;
      this.type = type;
   }
   
   public String getName() {
      return name;
   }
   
   public Type getType() {
      return type;
   }
}