package tern.core.function;

import java.util.List;

import tern.core.annotation.Annotation;
import tern.core.attribute.Attribute;

public interface Function extends Attribute {
   Signature getSignature();
   List<Annotation> getAnnotations();
   Invocation getInvocation();
   String getDescription();
   Object getProxy(Class type);
   Object getProxy();
}