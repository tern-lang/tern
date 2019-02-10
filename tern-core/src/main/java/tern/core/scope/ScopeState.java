package tern.core.scope;

import tern.core.constraint.Constraint;
import tern.core.variable.Value;

public interface ScopeState extends Iterable<String> {
   Value getValue(String name);
   Constraint getConstraint(String name);
   void addValue(String name, Value value);
   void addConstraint(String name, Constraint constraint);
}