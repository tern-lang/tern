package org.ternlang.tree.define;

import static org.ternlang.core.type.Category.OTHER;

import java.util.concurrent.atomic.AtomicBoolean;

import org.ternlang.core.Evaluation;
import org.ternlang.core.Execution;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.instance.Instance;
import org.ternlang.core.type.TypeState;
import org.ternlang.core.type.Category;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.Value;
import org.ternlang.core.result.Result;

public class ThisState extends TypeState {
   
   private final AtomicBoolean allocate;
   private final AtomicBoolean define;
   private final AtomicBoolean compile;
   private final Evaluation expression;
   private final Execution execution;
   
   public ThisState(Execution execution, Evaluation expression) {
      this.define = new AtomicBoolean();
      this.allocate = new AtomicBoolean();
      this.compile = new AtomicBoolean();
      this.expression = expression;
      this.execution = execution;
   }

   @Override
   public Category define(Scope instance, Type real) throws Exception {
      if(define.compareAndSet(false, true)) {
         expression.define(instance);
      }
      return OTHER;
   }
   
   @Override
   public void compile(Scope instance, Type real) throws Exception {
      if(compile.compareAndSet(false, true)) {
         expression.compile(instance, null);
      }
   }   
   
   @Override
   public void allocate(Scope instance, Type real) throws Exception {
      if(allocate.compareAndSet(false, true)) {
         execution.execute(instance);
      }
   }
   
   @Override
   public Result execute(Scope instance, Type real) throws Exception {
      Value value = expression.evaluate(instance, null);
      Instance result = value.getValue();
      
      return Result.getNormal(result); // this will return the instance created!!
   }
}