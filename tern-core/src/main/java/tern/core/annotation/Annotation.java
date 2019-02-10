package tern.core.annotation;

import tern.core.Any;

public interface Annotation extends Any{
   Object getAttribute(String name);
   String getName();
}