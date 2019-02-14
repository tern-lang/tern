package org.ternlang.core.resume;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public interface Promise<T> {
   T value();
   T value(long wait);
   T value(long wait, TimeUnit unit);
   Promise<T> join();
   Promise<T> join(long wait);
   Promise<T> join(long wait, TimeUnit unit);
   Promise<T> success(Task<T> task);
   Promise<T> success(Runnable task);
   Promise<T> failure(Task<Object> task);
   Promise<T> failure(Runnable task);
   Future<T> future();
}
