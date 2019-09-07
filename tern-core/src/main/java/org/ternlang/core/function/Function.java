package org.ternlang.core.function;

import java.util.List;

import org.ternlang.core.annotation.Annotation;
import org.ternlang.core.attribute.Attribute;

public interface Function extends Attribute {
   Object getAdapter();
   String getDescription();
   Signature getSignature();
   Invocation getInvocation();
   List<Annotation> getAnnotations();
   Object getProxy(Class type);
   Object getProxy();
}