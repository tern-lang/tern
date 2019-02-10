package tern.core.function.dispatch;

import java.util.List;

import tern.core.array.ArrayBuilder;
import tern.core.constraint.Constraint;
import tern.core.error.ErrorHandler;
import tern.core.function.Connection;
import tern.core.function.resolve.FunctionCall;
import tern.core.function.resolve.FunctionResolver;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.variable.Value;

public class ArrayDispatcher implements FunctionDispatcher {
   
   private final FunctionResolver resolver;
   private final ArrayBuilder builder;
   private final ErrorHandler handler;
   private final String name;
   
   public ArrayDispatcher(FunctionResolver resolver, ErrorHandler handler, String name) {
      this.builder = new ArrayBuilder();
      this.resolver = resolver;
      this.handler = handler;
      this.name = name;
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint constraint, Type... arguments) throws Exception {
      Type actual = constraint.getType(scope);
      Type list = builder.convert(actual);
      FunctionCall call = resolver.resolveInstance(scope, list, name, arguments);
      
      if(call == null) {
         handler.failCompileInvocation(scope, actual, name, arguments);
      }
      return call.check(scope, constraint, arguments);
   }

   @Override
   public Connection connect(Scope scope, Value value, Object... arguments) throws Exception {
      Object object = value.getValue();
      List list = builder.convert(object);
      FunctionCall call = resolver.resolveInstance(scope, list, name, arguments);
      
      if(call == null) {
         handler.failRuntimeInvocation(scope, object, name, arguments);
      }
      return new ArrayConnection(call, builder);
   }
   
   private static class ArrayConnection implements Connection<Value> {
      
      private final ArrayBuilder builder;
      private final FunctionCall call;
      
      public ArrayConnection(FunctionCall call, ArrayBuilder builder) {
         this.builder = builder;
         this.call = call;
      }

      @Override
      public Object invoke(Scope scope, Value value, Object... arguments) throws Exception {
         Object source = value.getValue();
         List list = builder.convert(source);
         
         return call.invoke(scope, list, arguments);
      }

      @Override
      public boolean match(Scope scope, Object object, Object... arguments) throws Exception {
         return call.match(scope, object, arguments);
      }
      
   }
}