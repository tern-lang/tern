package org.ternlang.core.attribute;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.scope.Scope;

public interface AttributeResult {
   Constraint getConstraint(Scope scope, Constraint left, Constraint... arguments) throws Exception;
}
