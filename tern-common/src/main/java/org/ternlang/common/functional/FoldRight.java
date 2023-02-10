package org.ternlang.common.functional;

import java.util.function.BiFunction;

@FunctionalInterface
public interface FoldRight<A, B, R> {
   R accept(BiFunction<A, B, B> function);
}
