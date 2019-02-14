package org.ternlang.core.variable.bind;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.scope.index.Address;
import org.ternlang.core.variable.Value;

public interface VariableResult<T> {
   Address getAddress(int offset);
   Constraint getConstraint(Constraint left);
   Value getValue(T left);
}
