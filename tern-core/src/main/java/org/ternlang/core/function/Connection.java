package org.ternlang.core.function;

import org.ternlang.core.scope.Scope;

public interface Connection<T> extends Invocation<T> {
   boolean match(Scope scope, Object object, Object... arguments) throws Exception;
}
