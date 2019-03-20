package org.ternlang.tree.resume;

import java.util.Iterator;

import org.ternlang.core.Context;
import org.ternlang.core.module.Module;
import org.ternlang.core.resume.Answer;
import org.ternlang.core.resume.Task;
import org.ternlang.core.resume.TaskScheduler;
import org.ternlang.core.resume.Yield;
import org.ternlang.core.scope.Scope;

public class YieldTaskBuilder {

   private final Module module;

   public YieldTaskBuilder(Module module) {
      this.module = module;
   }

   public Task<Answer> create(Yield yield) {
      Iterator<Object> iterator = yield.iterator();
      return new YieldTask(iterator, module);
   }

   private static class YieldTask implements Task<Answer> {

      private final Iterator<Object> iterator;
      private final Module module;

      public YieldTask(Iterator<Object> iterator, Module module) {
         this.iterator = iterator;
         this.module = module;
      }

      @Override
      public void execute(Answer answer) {
         Task<Object> task = new ResumeTask(iterator, answer, module);

         try {
            task.execute(null);
         } catch(Throwable cause){
            answer.failure(cause);
         }
      }
   }

   private static class ResumeTask implements Task<Object> {

      private final ResumeProcessor processor;
      private final Module module;

      public ResumeTask(Iterator<Object> iterator, Answer answer, Module module) {
         this.processor = new ResumeProcessor(iterator, this, answer);
         this.module = module;
      }

      public void execute(Object object) {
         Task task = processor.process(object);

         if(task != null) {
            Scope scope = module.getScope();
            Context context = module.getContext();
            TaskScheduler scheduler = context.getScheduler();

            scheduler.schedule(scope, task);
         }
      }
   }
}
