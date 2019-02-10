package tern.core.function.resolve;

import static tern.core.constraint.Constraint.NONE;
import static tern.core.function.index.Retention.ALWAYS;

import tern.core.constraint.Constraint;
import tern.core.function.Invocation;
import tern.core.function.index.FunctionPointer;
import tern.core.function.index.Retention;
import tern.core.function.index.ReturnType;
import tern.core.scope.Scope;
import tern.core.type.Type;

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

   public Constraint check(Scope scope, Constraint left, Type... types) throws Exception {
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
