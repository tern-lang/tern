package org.ternlang.tree.collection;

import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;

public interface Iteration {
   Type getEntry(Scope scope) throws Exception;
   Iterable getIterable(Scope scope) throws Exception;
}