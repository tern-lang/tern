package org.ternlang.core.function.resolve;

import org.ternlang.core.function.Connection;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.variable.Value;

public class FunctionConnection implements Connection<Value> {
   
   protected final FunctionCall call;
   
   public FunctionConnection(FunctionCall call) {
      this.call = call;
   }

   @Override
   public boolean match(Scope scope, Object object, Object... arguments) throws Exception {
      return call.match(scope, object, arguments);
   }
   
   @Override
   public Object invoke(Scope scope, Value value, Object... arguments) throws Exception {
      Object source = value.getValue();
      return call.invoke(scope, source, arguments);
   }
}