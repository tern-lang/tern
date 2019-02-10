package tern.core.constraint.transform;

import tern.core.constraint.Constraint;
import tern.core.scope.Scope;

public interface ConstraintIndex {
   Constraint update(Scope scope, Constraint source, Constraint change);
}
