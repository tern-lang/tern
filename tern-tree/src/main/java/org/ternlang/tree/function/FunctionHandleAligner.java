package org.ternlang.tree.function;

import org.ternlang.core.type.index.ScopeType;
import org.ternlang.core.variable.Value;

public class FunctionHandleAligner {
   
   private final Value value;
   private final boolean constructor;

   public FunctionHandleAligner(Value value, boolean constructor){
      this.constructor = constructor;
      this.value = value;
   }
   
   public Object[] align(Object... list) throws Exception {      
      if(constructor) {
         Object object = value.getValue();
         
         if(ScopeType.class.isInstance(object)) { // inject type parameter
            Object[] arguments = new Object[list.length +1];
         
            for(int i = 0; i < list.length; i++) {
               arguments[i + 1] = list[i];
            }
            arguments[0] = object;
            return arguments;
         }
      }
      return list;
   }
}