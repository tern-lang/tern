package tern.core.resume;

public class RunnableTask implements Task {

   private final Runnable task;

   public RunnableTask(Runnable task) {
      this.task = task;
   }

   @Override
   public void execute(Object value) {
      task.run();
   }
}
