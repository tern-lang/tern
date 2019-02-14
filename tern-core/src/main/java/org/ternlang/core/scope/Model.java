package org.ternlang.core.scope;

import org.ternlang.core.Any;

public interface Model extends Any {
   Object getAttribute(String name);
}