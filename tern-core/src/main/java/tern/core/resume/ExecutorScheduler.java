package tern.core.resume;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import tern.core.error.ErrorCauseExtractor;
import tern.core.error.ErrorHandler;
import tern.core.scope.Scope;
import tern.core.variable.Value;

public class ExecutorScheduler implements TaskScheduler {

   private final ErrorHandler handler;
   private final Executor executor;

   public ExecutorScheduler(ErrorHandler handler, Executor executor) {
      this.executor = executor;
      this.handler = handler;
   }

   @Override
   public Promise schedule(Scope scope, Task task) {
      PromiseDelegate promise = new PromiseDelegate(handler, executor, scope, task);

      if(task != null) {
         promise.execute();
      }
      return promise;
   }

   private static class PromiseDelegate implements Promise {

      private final PromiseFuture future;
      private final PromiseAnswer answer;
      private final PromiseTask task;
      private final Executor executor;

      public PromiseDelegate(ErrorHandler handler, Executor executor, Scope scope, Task task) {
         this.future = new PromiseFuture(handler, scope);
         this.answer = new PromiseAnswer(future, handler, scope);
         this.task = new PromiseTask(answer, task);
         this.executor = executor;
      }

      @Override
      public Future future() {
         return future.future();
      }

      @Override
      public Object value() {
         return future.get();
      }

      @Override
      public Object value(long wait) {
         return future.get(wait, MILLISECONDS);
      }

      @Override
      public Object value(long wait, TimeUnit unit) {
         return future.get(wait, unit);
      }

      @Override
      public Promise join() {
         try {
            future.get();
         } catch(Throwable e){
            return this;
         }
         return this;
      }

      @Override
      public Promise join(long wait) {
         try {
            future.get(wait, MILLISECONDS);
         } catch(Throwable e){
            return this;
         }
         return this;
      }

      @Override
      public Promise join(long wait, TimeUnit unit) {
         try {
            future.get(wait, unit);
         } catch(Throwable e){
            return this;
         }
         return this;
      }

      @Override
      public Promise failure(Task task) {
         if(task != null) {
            future.failure(task);
            future.error();
         }
         return this;
      }

      @Override
      public Promise failure(Runnable task) {
         Task adapter = new RunnableTask(task);

         if(task != null) {
            future.failure(adapter);
            future.error();
         }
         return this;
      }

      @Override
      public Promise success(Task task) {
         if(task != null) {
            future.success(task);
            future.complete();
         }
         return this;
      }

      @Override
      public Promise success(Runnable task) {
         Task adapter = new RunnableTask(task);

         if(task != null) {
            future.success(adapter);
            future.complete();
         }
         return this;
      }

      public Promise execute() {
         if(executor != null) {
            executor.execute(task);
         } else {
            task.run();
         }
         return this;
      }
   }

   private static class PromiseFuture implements Callable {

      private final AtomicReference<Object> error;
      private final AtomicReference<Value> success;
      private final BlockingQueue<Task> listeners;
      private final BlockingQueue<Task> failures;
      private final FutureTask<Value> task;
      private final ErrorHandler handler;
      private final Scope scope;

      public PromiseFuture(ErrorHandler handler, Scope scope) {
         this.failures = new LinkedBlockingQueue<Task>();
         this.listeners = new LinkedBlockingQueue<Task>();
         this.success = new AtomicReference<Value>();
         this.error = new AtomicReference<Object>();
         this.task = new FutureTask(this);
         this.handler = handler;
         this.scope = scope;
      }

      public Future future() {
         return task;
      }

      @Override
      public Object call() {
         Value value = success.get();

         if(value != null) {
            return value.getValue();
         }
         return null;
      }

      public Object get() {
         try {
            Object result = task.get();
            Object cause = error.get();

            if(cause != null) {
               return handler.failInternalError(scope, cause);
            }
            return result;
         } catch (Exception e) {
            return handler.failInternalError(scope, e);
         }
      }

      public Object get(long wait, TimeUnit unit) {
         try {
            Object result = task.get(wait, unit);
            Object cause = error.get();

            if(cause != null) {
               return handler.failInternalError(scope, cause);
            }
            return result;
         } catch (Exception e) {
            return handler.failInternalError(scope, e);
         }
      }

      public void complete() {
         Value value = success.get();

         if (value != null) {
            Object object = value.getValue();

            while (!listeners.isEmpty()) {
               Task listener = listeners.poll();

               if (listener != null) {
                  listener.execute(object);
               }
            }
         }
      }

      public void error() {
         Object cause = error.get();

         if (cause != null) {
            while (!failures.isEmpty()) {
               Task failure = failures.poll();

               if (failure != null) {
                  failure.execute(cause);
               }
            }
         }
      }

      public void success(Task task) {
         listeners.add(task);
      }

      public void failure(Task task) {
         failures.add(task);
      }

      public void success(Value value) {
         success.compareAndSet(null, value);
         task.run();
      }

      public void failure(Object cause) {
         error.compareAndSet(null, cause);
         task.run();
      }
   }

   private static class PromiseAnswer implements Answer {

      private final ErrorCauseExtractor extractor;
      private final PromiseFuture future;
      private final ErrorHandler handler;
      private final Scope scope;

      public PromiseAnswer(PromiseFuture future, ErrorHandler handler, Scope scope) {
         this.extractor = new ErrorCauseExtractor();
         this.handler = handler;
         this.future = future;
         this.scope = scope;
      }

      @Override
      public void success(Object result) {
         Value value = Value.getTransient(result);

         try {
            future.success(value);
            future.complete();
         } catch(Exception e) {
            handler.failInternalError(scope, e);
         }
      }

      @Override
      public void failure(Throwable cause) {
         Object value = extractor.extract(cause);

         try {
            future.failure(value);
            future.error();
         } catch(Exception e) {
            handler.failInternalError(scope, e);
         }
      }
   }

   private static class PromiseTask implements Runnable {

      private final PromiseAnswer answer;
      private final Task task;

      public PromiseTask(PromiseAnswer answer, Task task) {
         this.answer = answer;
         this.task = task;
      }

      @Override
      public void run() {
         try {
            task.execute(answer);
         }catch(Exception cause){
            answer.failure(cause);
         }
      }
   }
}
