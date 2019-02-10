package tern.core.property;

import java.util.List;

import tern.core.annotation.Annotation;
import tern.core.attribute.Attribute;

public interface Property<T> extends Attribute {
   String getAlias();
   List<Annotation> getAnnotations();
   Object getValue(T source);
   void setValue(T source, Object value);
}