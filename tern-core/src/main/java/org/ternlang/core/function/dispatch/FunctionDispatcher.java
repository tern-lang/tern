package org.ternlang.core.function.dispatch;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.function.Connection;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.Value;
                   
public interface FunctionDispatcher {
   Constraint compile(Scope scope, Constraint constraint, Type... arguments) throws Exception;
   Connection connect(Scope scope, Value value, Object... arguments) throws Exception;
}