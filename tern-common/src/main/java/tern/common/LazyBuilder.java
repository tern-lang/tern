package tern.common;

public interface LazyBuilder<K, V> {
   V create(K key);
}
