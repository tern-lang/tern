package org.ternlang.common;

public interface Consumer<V, R> {
   R consume(V value);
}
