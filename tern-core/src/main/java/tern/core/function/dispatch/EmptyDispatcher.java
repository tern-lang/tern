package tern.core.function.dispatch;

import static tern.core.constraint.Constraint.NONE;

import tern.core.constraint.Constraint;
import tern.core.function.Connection;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.variable.Value;

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
