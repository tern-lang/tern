package org.ternlang.tree.function;

import org.ternlang.core.scope.Scope;

public interface ParameterAppender {
   void append(Scope scope, Object[] arguments) throws Exception;
}
