package tern.core.function;

import tern.core.scope.Scope;

public interface Connection<T> extends Invocation<T> {
   boolean match(Scope scope, Object object, Object... arguments) throws Exception;
}
