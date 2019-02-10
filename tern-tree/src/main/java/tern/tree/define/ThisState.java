package tern.tree.define;

import static tern.core.type.Category.OTHER;

import java.util.concurrent.atomic.AtomicBoolean;

import tern.core.Evaluation;
import tern.core.Execution;
import tern.core.scope.Scope;
import tern.core.scope.instance.Instance;
import tern.core.type.TypeState;
import tern.core.type.Category;
import tern.core.type.Type;
import tern.core.variable.Value;
import tern.core.result.Result;

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