package org.ternlang.core.property;

import java.util.List;

import org.ternlang.core.annotation.Annotation;
import org.ternlang.core.attribute.Attribute;

public interface Property<T> extends Attribute {
   String getAlias();
   List<Annotation> getAnnotations();
   Object getValue(T source);
   void setValue(T source, Object value);
}