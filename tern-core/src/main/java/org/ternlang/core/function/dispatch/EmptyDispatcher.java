package org.ternlang.core.function.dispatch;

import static org.ternlang.core.constraint.Constraint.NONE;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.function.Connection;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.Value;

public class EmptyDispatcher implements FunctionDispatcher {
   
   private final Connection connection;
   
   public EmptyDispatcher() {
      this.connection = new EmptyConnection();
   }

   @Override
   public Constraint compile(Scope scope, Constraint constraint, Type... arguments) throws Exception {
      return NONE;
   }

   @Override
   public Connection connect(Scope scope, Value value, Object... arguments) throws Exception {
      return connection;
   }
   
   private static class EmptyConnection implements Connection<Value> {

      public EmptyConnection() {
         super();
      }

      @Override
      public boolean match(Scope scope, Object object, Object... arguments) throws Exception {
         return false;
      }
      
      @Override
      public Object invoke(Scope scope, Value value, Object... arguments) throws Exception {
         return null;
      }
   }
}      
