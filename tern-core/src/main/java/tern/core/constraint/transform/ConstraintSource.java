package tern.core.constraint.transform;

import java.util.List;

import tern.core.constraint.Constraint;

public interface ConstraintSource {
   List<Constraint> getConstraints(Constraint constraint);
}
