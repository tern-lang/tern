package tern.core.attribute;

import tern.core.constraint.Constraint;
import tern.core.scope.Scope;
import tern.core.type.Type;

public interface AttributeResult {
   Constraint getConstraint(Scope scope, Constraint left, Type... types) throws Exception;
}
