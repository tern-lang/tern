package tern.core.type;

import java.util.List;

import tern.core.Entity;
import tern.core.annotation.Annotation;
import tern.core.constraint.Constraint;
import tern.core.function.Function;
import tern.core.module.Module;
import tern.core.property.Property;

public interface Type extends Entity {
   List<Constraint> getGenerics();
   List<Annotation> getAnnotations();
   List<Property> getProperties();
   List<Function> getFunctions();
   List<Constraint> getTypes();
   Module getModule();
   Class getType();
   Type getOuter();
   Type getEntry();
}