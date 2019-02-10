package tern.tree.collection;

import tern.core.scope.Scope;
import tern.core.type.Type;

public interface Iteration {
   Type getEntry(Scope scope) throws Exception;
   Iterable getIterable(Scope scope) throws Exception;
}