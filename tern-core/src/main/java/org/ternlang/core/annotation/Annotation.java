package org.ternlang.core.annotation;

import org.ternlang.core.Any;

public interface Annotation extends Any{
   Object getAttribute(String name);
   String getName();
}