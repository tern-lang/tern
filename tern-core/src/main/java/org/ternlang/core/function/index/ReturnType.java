package org.ternlang.core.function.index;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.type.Type;

public interface ReturnType {
   Constraint check(Constraint left, Type[] types) throws Exception;
}
