package tern.core.variable.bind;

import tern.core.constraint.Constraint;
import tern.core.scope.index.Address;
import tern.core.variable.Value;

public interface VariableResult<T> {
   Address getAddress(int offset);
   Constraint getConstraint(Constraint left);
   Value getValue(T left);
}
