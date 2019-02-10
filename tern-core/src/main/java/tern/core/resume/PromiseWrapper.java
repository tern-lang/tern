package tern.core.resume;

import static tern.core.constraint.Constraint.NONE;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import tern.core.constraint.Constraint;
import tern.core.scope.Scope;
import tern.core.type.Type;

public class PromiseWrapper {

   public PromiseWrapper() {
      super();
   }

   public Promise toPromise(Scope scope, Object object) {
      if(!Promise.class.isInstance(object)) {
         PromiseDelegate promise = new PromiseDelegate(object);
         return promise.execute();
      }
      return (Promise)object;
   }

   public Object fromPromise(Scope scope, Object object) {
      if(Promise.class.isInstance(object)) {
         Promise promise = (Promise)object;
         return promise.value();
      }
      return object;
   }

   public Constraint fromPromise(Scope scope, Constraint returns) {
      Type type = returns.getType(scope);

      if(type != null) {
         Class real = type.getType();

         if(real != null) {
            if (Promise.class.isAssignableFrom(real)) {
               List<Constraint> generics = returns.getGenerics(scope);

               for (Constraint generic : generics) {
                  return generic;
               }
               return NONE;
            }
         }
      }
      return returns;
   }

   private static class PromiseDelegate implements Promise {

      private final FutureTask<Object> future;
      private final Callable<Object> value;
      private final Object object;

      private PromiseDelegate(Object object) {
         this.value = new PromiseValue(object);
         this.future = new FutureTask<Object>(value);
         this.object = object;
      }

      @Override
      public Future future() {
         return future;
      }

      @Override
      public Object value() {
         return object;
      }

      @Override
      public Object value(long wait) {
         return object;
      }

      @Override
      public Object value(long wait, TimeUnit unit) {
         return object;
      }

      @Override
      public Promise join() {
         return this;
      }

      @Override
      public Promise join(long wait) {
         return this;
      }

      @Override
      public Promise join(long wait, TimeUnit unit) {
         return this;
      }

      @Override
      public Promise success(Task task) {
         if(task != null) {
            task.execute(object);
         }
         return this;
      }

      @Override
      public Promise success(Runnable task) {
         if(task != null) {
            task.run();
         }
         return this;
      }

      @Override
      public Promise failure(Task task) {
         return this;
      }

      @Override
      public Promise failure(Runnable task) {
         return this;
      }

      public Promise execute() {
         future.run();
         return this;
      }
   }

   private static class PromiseValue implements Callable<Object> {

      public final Object object;

      private PromiseValue(Object object) {
         this.object = object;
      }

      @Override
      public Object call() throws Exception {
         return object;
      }
   }
}
