package org.ternlang.core.attribute;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;

public interface AttributeResult {
   Constraint getConstraint(Scope scope, Constraint left, Type... types) throws Exception;
}
