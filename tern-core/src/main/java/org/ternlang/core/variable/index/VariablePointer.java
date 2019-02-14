package org.ternlang.core.variable.index;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.variable.Value;

public interface VariablePointer<T> {
   Constraint getConstraint(Scope scope, Constraint left);
   Value getValue(Scope scope, T left);
}