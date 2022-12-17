package org.ternlang.tree.define;

import org.ternlang.core.Execution;
import org.ternlang.core.Statement;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.function.Parameter;
import org.ternlang.core.function.Signature;
import org.ternlang.core.result.Result;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.ScopeBinder;
import org.ternlang.core.scope.ScopeState;
import org.ternlang.core.scope.index.Address;
import org.ternlang.core.scope.index.ScopeTable;
import org.ternlang.core.variable.Value;

import java.util.List;

public class ImplicitBody extends Statement {

   private final Execution execution;

   public ImplicitBody(Signature signature) {
      this.execution = new CompileExecution(signature);
   }

   @Override
   public Execution compile(Scope scope, Constraint returns) throws Exception {
      return execution;
   }

   private static class CompileExecution extends Execution {

      private final Signature signature;
      private final ScopeBinder binder;

      public CompileExecution(Signature signature) {
         this.binder = new ScopeBinder();
         this.signature = signature;
      }

      @Override
      public Result execute(Scope scope) throws Exception {
         Scope instance = binder.bind(scope, scope);
         ScopeTable table = scope.getTable();
         ScopeState inner = instance.getState();
         List<Parameter> parameters = signature.getParameters();
         int count = parameters.size();

         for (int i = 1; i < count; i++) {
            Parameter parameter = parameters.get(i);
            Address address = parameter.getAddress();
            String name = parameter.getName();
            Value value = table.getValue(address);
            Value field = inner.getValue(name);
            Object object = value.getValue();

            field.setValue(object);
         }
         return Result.getReturn(instance);
      }
   }
}
