package org.ternlang.common;

public interface Predicate<T> {
   boolean accept(T value);
}
