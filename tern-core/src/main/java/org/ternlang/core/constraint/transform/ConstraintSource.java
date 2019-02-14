package org.ternlang.core.constraint.transform;

import java.util.List;

import org.ternlang.core.constraint.Constraint;

public interface ConstraintSource {
   List<Constraint> getConstraints(Constraint constraint);
}
