package org.ternlang.common.functional;

import java.util.function.Function;

@FunctionalInterface
public interface Fold<A, B> {
   B accept(Function<A, B> function);
}
