package org.ternlang.tree.resume;

import java.util.Iterator;

import org.ternlang.core.Execution;
import org.ternlang.core.result.Result;
import org.ternlang.core.resume.Answer;
import org.ternlang.core.resume.Promise;
import org.ternlang.core.resume.PromiseWrapper;
import org.ternlang.core.resume.Task;
import org.ternlang.core.resume.TaskScheduler;
import org.ternlang.core.resume.Yield;
import org.ternlang.core.scope.Scope;

public class AsyncExecution extends Execution {

   private final TaskScheduler scheduler;
   private final PromiseWrapper wrapper;
   private final Execution execution;

   public AsyncExecution(TaskScheduler scheduler, Execution execution) {
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
      Iterator<Object> iterator = yield.iterator();
      Task<Answer> task = new AnswerTask(iterator);
      Promise promise = scheduler.schedule(scope, task);

      return Result.getNormal(promise);
   }

   private static class AnswerTask implements Task<Answer> {

      private final Iterator<Object> iterator;

      public AnswerTask(Iterator<Object> iterator) {
         this.iterator = iterator;
      }

      @Override
      public void execute(Answer answer) {
         Task<Object> task = new ResumeTask(iterator, answer);

         try {
            task.execute(null);
         } catch(Throwable cause){
            answer.failure(cause);
         }
      }
   }

   private static class ResumeTask implements Task<Object> {

      private final Iterator<Object> iterator;
      private final Answer answer;

      public ResumeTask(Iterator<Object> iterator, Answer answer) {
         this.iterator = iterator;
         this.answer = answer;
      }

      @Override
      public void execute(Object object) {
         try {
            while (iterator.hasNext()) {
               object = iterator.next();

               if(object != null) {
                  if (Promise.class.isInstance(object)) {
                     Promise promise = (Promise) object;
                     promise.success(this);
                     promise.failure(this);
                     return;
                  }
               }
            }
            answer.success(object);
         } catch(Throwable cause){
            answer.failure(cause);
         }
      }
   }
}
