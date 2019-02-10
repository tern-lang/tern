package tern.core.type.index;

import java.lang.reflect.Type;

import tern.core.constraint.Constraint;

public interface GenericConverter<T extends Type> {
   Constraint convert(T type, String name, int modifiers);
}
