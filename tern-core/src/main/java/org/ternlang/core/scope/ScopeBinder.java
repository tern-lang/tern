package org.ternlang.core.scope;

import org.ternlang.core.variable.Value;

public class ScopeBinder {
   
   public ScopeBinder() {
      super();
   }

   public Scope bind(Scope scope, Scope instance) {
      if(instance != null) {
         Value value = instance.getThis();
         
         if(value != null) {
            return value.getValue();
         }
      }
      return scope;
   }
}