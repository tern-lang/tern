package org.ternlang.core.constraint.transform;

import org.ternlang.core.constraint.Constraint;

public interface ConstraintTransform {
   ConstraintRule apply(Constraint left);
}
