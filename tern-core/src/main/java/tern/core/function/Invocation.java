package tern.core.function;

import tern.core.scope.Scope;

public interface Invocation<T> {
   Object invoke(Scope scope, T object, Object... list) throws Exception;
}