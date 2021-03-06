package org.ternlang.core.function.dispatch;

import java.util.List;

import org.ternlang.core.array.ArrayBuilder;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.ErrorHandler;
import org.ternlang.core.function.ArgumentListCompiler;
import org.ternlang.core.function.Connection;
import org.ternlang.core.function.resolve.FunctionCall;
import org.ternlang.core.function.resolve.FunctionResolver;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.Value;

public class ArrayDispatcher implements FunctionDispatcher {
   
   private final ArgumentListCompiler compiler;
   private final FunctionResolver resolver;
   private final ArrayBuilder builder;
   private final ErrorHandler handler;
   private final String name;
   
   public ArrayDispatcher(FunctionResolver resolver, ErrorHandler handler, String name) {
      this.compiler = new ArgumentListCompiler();
      this.builder = new ArrayBuilder();
      this.resolver = resolver;
      this.handler = handler;
      this.name = name;
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint constraint, Constraint... arguments) throws Exception {
      Type actual = constraint.getType(scope);
      Type list = builder.convert(actual);
      Type[] types = compiler.compile(scope, arguments);
      FunctionCall call = resolver.resolveInstance(scope, list, name, types);
      
      if(call == null) {
         handler.failCompileInvocation(scope, actual, name, types);
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