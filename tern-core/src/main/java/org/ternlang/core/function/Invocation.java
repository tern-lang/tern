package org.ternlang.core.function;

import org.ternlang.core.scope.Scope;

public interface Invocation<T> {
   Object invoke(Scope scope, T object, Object... list) throws Exception;
}