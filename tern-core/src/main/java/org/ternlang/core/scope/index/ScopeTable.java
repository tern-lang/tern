package org.ternlang.core.scope.index;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.variable.Value;

public interface ScopeTable extends Iterable<Value> {
   Value getValue(Address address);
   Constraint getConstraint(Address address);
   void addValue(Address address, Value value);
   void addConstraint(Address address, Constraint constraint);
}
