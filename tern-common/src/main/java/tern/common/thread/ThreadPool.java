package tern.common.thread;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPool implements Executor {

   private final BlockingQueue<Runnable> queue;
   private final ThreadFactory factory;
   private final Executor executor;
   
   public ThreadPool() {
      this(1);
   }
   
   public ThreadPool(int core) {
      this(core, 0);
   }
   
   public ThreadPool(int core, int stack) {
      this(core, core, stack);
   }

   public ThreadPool(int core, int maximum, int stack) {
      this.queue = new LinkedBlockingQueue<Runnable>();
      this.factory = new ThreadBuilder(true, stack);
      this.executor = new ThreadPoolExecutor(core, maximum, 60L, SECONDS, queue, factory);
   }

   @Override
   public void execute(Runnable command) {
      executor.execute(command);
   }
}