package org.ternlang.core.scope;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.variable.Value;

public interface ScopeState extends Iterable<String> {
   Value getValue(String name);
   Constraint getConstraint(String name);
   void addValue(String name, Value value);
   void addConstraint(String name, Constraint constraint);
}