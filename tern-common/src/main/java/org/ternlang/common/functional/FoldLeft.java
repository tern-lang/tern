package org.ternlang.common.functional;

import java.util.function.BiFunction;

@FunctionalInterface
public interface FoldLeft<A, B> {
   B accept(BiFunction<B, A, B> function);
}
