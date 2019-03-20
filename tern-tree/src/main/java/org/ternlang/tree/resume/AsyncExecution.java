package org.ternlang.tree.resume;

import org.ternlang.core.Execution;
import org.ternlang.core.module.Module;
import org.ternlang.core.result.Result;
import org.ternlang.core.resume.Answer;
import org.ternlang.core.resume.Promise;
import org.ternlang.core.resume.PromiseWrapper;
import org.ternlang.core.resume.Task;
import org.ternlang.core.resume.TaskScheduler;
import org.ternlang.core.resume.Yield;
import org.ternlang.core.scope.Scope;

public class AsyncExecution extends Execution {

   private final YieldTaskBuilder converter;
   private final TaskScheduler scheduler;
   private final PromiseWrapper wrapper;
   private final Execution execution;

   public AsyncExecution(TaskScheduler scheduler, Execution execution, Module module) {
      this.converter = new YieldTaskBuilder(module);
      this.wrapper = new PromiseWrapper();
      this.scheduler = scheduler;
      this.execution = execution;
   }

   @Override
   public Result execute(Scope scope) throws Exception {
      Result result = execution.execute(scope);

      if(!result.isAwait()) {
         Object value = result.getValue();
         Promise promise = wrapper.toPromise(scope, value);

         return Result.getNormal(promise);
      }
      return execute(scope, result);
   }

   private Result execute(Scope scope, Result result) throws Exception {
      Yield yield = result.getValue();
      Task<Answer> task = converter.create(yield);
      Promise promise = scheduler.schedule(scope, task);

      return Result.getNormal(promise);
   }
}
