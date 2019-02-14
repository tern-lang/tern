package org.ternlang.core.resume;

public interface Task<T> {
   void execute(T value);
}
