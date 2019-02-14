package org.ternlang.tree.function;

import org.ternlang.core.scope.Scope;
import org.ternlang.core.variable.Value;

public interface ScopeMatcher {
   Value compile(Scope scope) throws Exception;
   Value execute(Scope scope) throws Exception;
}
