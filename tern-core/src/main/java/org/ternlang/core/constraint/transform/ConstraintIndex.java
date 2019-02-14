package org.ternlang.core.constraint.transform;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.scope.Scope;

public interface ConstraintIndex {
   Constraint update(Scope scope, Constraint source, Constraint change);
}
