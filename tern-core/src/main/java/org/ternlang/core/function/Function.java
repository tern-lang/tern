package org.ternlang.core.function;

import java.util.List;

import org.ternlang.core.annotation.Annotation;
import org.ternlang.core.attribute.Attribute;

public interface Function extends Attribute {
   Signature getSignature();
   List<Annotation> getAnnotations();
   Invocation getInvocation();
   String getDescription();
   Object getProxy(Class type);
   Object getProxy();
}