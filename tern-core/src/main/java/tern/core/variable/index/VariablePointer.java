package tern.core.variable.index;

import tern.core.constraint.Constraint;
import tern.core.scope.Scope;
import tern.core.variable.Value;

public interface VariablePointer<T> {
   Constraint getConstraint(Scope scope, Constraint left);
   Value getValue(Scope scope, T left);
}