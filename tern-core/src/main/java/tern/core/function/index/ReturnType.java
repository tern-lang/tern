package tern.core.function.index;

import tern.core.constraint.Constraint;
import tern.core.type.Type;

public interface ReturnType {
   Constraint check(Constraint left, Type[] types) throws Exception;
}
