package org.ternlang.core.type;

import java.util.List;

import org.ternlang.core.Entity;
import org.ternlang.core.annotation.Annotation;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.function.Function;
import org.ternlang.core.module.Module;
import org.ternlang.core.property.Property;

public interface Type extends Entity {
   List<Constraint> getGenerics();
   List<Annotation> getAnnotations();
   List<Property> getProperties();
   List<Function> getFunctions();
   List<Constraint> getTypes(); // hierarchy
   Module getModule();
   Class getType();
   Type getOuter(); // enclosing type
   Type getEntry(); // array entry
}