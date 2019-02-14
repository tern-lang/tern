package org.ternlang.core.function.dispatch;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.convert.proxy.Delegate;
import org.ternlang.core.error.ErrorHandler;
import org.ternlang.core.function.Connection;
import org.ternlang.core.function.resolve.FunctionCall;
import org.ternlang.core.function.resolve.FunctionConnection;
import org.ternlang.core.function.resolve.FunctionResolver;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.Value;

public class DelegateDispatcher implements FunctionDispatcher {
   
   private final FunctionResolver resolver;
   private final ErrorHandler handler;
   private final String name;      
   
   public DelegateDispatcher(FunctionResolver resolver, ErrorHandler handler, String name) {
      this.resolver = resolver;
      this.handler = handler;
      this.name = name;
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint constraint, Type... arguments) throws Exception {
      Type object = constraint.getType(scope);
      FunctionCall call = resolver.resolveFunction(scope, object, name, arguments);
      
      if(call == null) {
         handler.failCompileInvocation(scope, object, name, arguments);
      }
      return call.check(scope, constraint, arguments);
   }
   
   @Override
   public Connection connect(Scope scope, Value value, Object... arguments) throws Exception {
      Delegate object = value.getValue();
      FunctionCall call = resolver.resolveFunction(scope, object, name, arguments);
      
      if(call == null) {
         handler.failRuntimeInvocation(scope, object, name, arguments);
      }
      return new FunctionConnection(call);
   }
}