package org.ternlang.core.attribute;

import java.util.List;

import org.ternlang.core.Handle;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.type.Type;

public interface Attribute extends Handle {
   String getName();
   Type getSource(); // declaring type
   List<Constraint> getGenerics();
   Constraint getConstraint();
   int getModifiers();
}