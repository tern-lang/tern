package org.ternlang.core.link;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.ternlang.core.Entity;

public class ImportFuture<T extends Entity> implements Future<T>{

   private final T entity;
   
   public ImportFuture(T entity) {
      this.entity = entity;
   }
   
   @Override
   public boolean cancel(boolean interrupt) {
      return false;
   }

   @Override
   public boolean isCancelled() {
      return false;
   }

   @Override
   public boolean isDone() {
      return true;
   }

   @Override
   public T get() throws InterruptedException, ExecutionException {
      return entity;
   }

   @Override
   public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
      return entity;
   }

}
