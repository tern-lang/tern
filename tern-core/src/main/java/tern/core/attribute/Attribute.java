package tern.core.attribute;

import java.util.List;

import tern.core.Handle;
import tern.core.constraint.Constraint;
import tern.core.type.Type;

public interface Attribute extends Handle {
   String getName();
   Type getSource(); // declaring type
   List<Constraint> getGenerics();
   Constraint getConstraint();
   int getModifiers();
}