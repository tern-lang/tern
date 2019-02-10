package tern.tree.function;

import tern.core.scope.Scope;
import tern.core.variable.Value;

public interface ScopeMatcher {
   Value compile(Scope scope) throws Exception;
   Value execute(Scope scope) throws Exception;
}
