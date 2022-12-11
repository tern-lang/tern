package org.ternlang.core;

public enum Expansion {
   NORMAL,
   CLOSURE;

   public boolean isNormal() {
      return this == NORMAL;
   }

   public boolean isClosure() {
      return this == CLOSURE;
   }
}
