package org.ternlang.core.function;

import java.lang.reflect.Member;
import java.util.List;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.type.Type;

public interface Signature {
   List<Constraint> getGenerics();
   List<Parameter> getParameters();
   ArgumentConverter getConverter();
   Type getDefinition();
   Origin getOrigin();
   Member getSource();
   boolean isVariable();
   boolean isAbsolute(); // does it have absolute parameters
}