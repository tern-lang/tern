package org.ternlang.core.function.resolve;

import static org.ternlang.core.constraint.Constraint.NONE;
import static org.ternlang.core.function.index.Retention.ALWAYS;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.function.Invocation;
import org.ternlang.core.function.index.FunctionPointer;
import org.ternlang.core.function.index.Retention;
import org.ternlang.core.function.index.ReturnType;
import org.ternlang.core.scope.Scope;

public class FunctionCall implements Invocation {
   
   private final FunctionPointer pointer;
   
   public FunctionCall(FunctionPointer pointer) {
      this.pointer = pointer;
   }
   
   public boolean match(Scope scope, Object object, Object... list) throws Exception {
      Retention retention =  pointer.getRetention();
      
      if(retention == ALWAYS) {
         return true;
      }
      return false;
   }

   public Constraint check(Scope scope, Constraint left, Constraint... types) throws Exception {
      ReturnType type = pointer.getType(scope);

      if(type != null) {
         return type.check(left, types);
      }
      return NONE;
   }
   
   @Override
   public Object invoke(Scope scope, Object object, Object... list) throws Exception {
      Invocation invocation = pointer.getInvocation();
      
      if(invocation != null) {
         return invocation.invoke(scope, object, list);
      }
      return null;
   } 
}
