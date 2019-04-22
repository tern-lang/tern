package org.ternlang.common;

public interface Guard<T> {
   boolean require(T value); 
   boolean done(T value);
}
