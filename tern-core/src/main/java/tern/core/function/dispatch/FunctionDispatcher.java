package tern.core.function.dispatch;

import tern.core.constraint.Constraint;
import tern.core.function.Connection;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.variable.Value;
                   
public interface FunctionDispatcher {
   Constraint compile(Scope scope, Constraint constraint, Type... arguments) throws Exception;
   Connection connect(Scope scope, Value value, Object... arguments) throws Exception;
}