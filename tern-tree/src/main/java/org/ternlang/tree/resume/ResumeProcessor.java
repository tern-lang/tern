package org.ternlang.tree.resume;

import java.util.Iterator;

import org.ternlang.core.resume.Answer;
import org.ternlang.core.resume.Promise;
import org.ternlang.core.resume.Task;

public class ResumeProcessor {

   private final Iterator<Object> iterator;
   private final Task<Object> task;
   private final Answer answer;

   public ResumeProcessor(Iterator<Object> iterator, Task<Object> task, Answer answer) {
      this.iterator = iterator;
      this.answer = answer;
      this.task = task;
   }

   public Task<Object> process(Object object) {
      try {
         while (iterator.hasNext()) {
            object = iterator.next();

            if(object != null) {
               if (Promise.class.isInstance(object)) {
                  Promise promise = (Promise) object;
                  promise.success(task);
                  promise.failure(task);
                  return null;
               }
            }
         }
      } catch(Throwable cause){
         return new CompleteTask(answer, null, cause);
      }
      return new CompleteTask(answer, object, null);
   }

   private static class CompleteTask implements Task<Object> {

      private final Throwable cause;
      private final Answer answer;
      private final Object object;

      public CompleteTask(Answer answer, Object object, Throwable cause) {
         this.answer = answer;
         this.object = object;
         this.cause = cause;
      }

      @Override
      public void execute(Object value) {
         if(cause != null) {
            answer.failure(cause);
         } else {
            answer.success(object);
         }
      }
   }
}
