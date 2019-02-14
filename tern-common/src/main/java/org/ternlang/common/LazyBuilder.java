package org.ternlang.common;

public interface LazyBuilder<K, V> {
   V create(K key);
}
