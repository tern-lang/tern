package tern.core.constraint.transform;

import tern.core.constraint.Constraint;

public interface ConstraintTransform {
   ConstraintRule apply(Constraint left);
}
