package org.ternlang.core.function.index;

import org.ternlang.core.constraint.Constraint;

public interface ReturnType {
   Constraint check(Constraint left, Constraint[] types) throws Exception;
}
