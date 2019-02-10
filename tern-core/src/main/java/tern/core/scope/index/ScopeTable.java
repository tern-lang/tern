package tern.core.scope.index;

import tern.core.constraint.Constraint;
import tern.core.variable.Value;

public interface ScopeTable extends Iterable<Value> {
   Value getValue(Address address);
   Constraint getConstraint(Address address);
   void addValue(Address address, Value value);
   void addConstraint(Address address, Constraint constraint);
}
