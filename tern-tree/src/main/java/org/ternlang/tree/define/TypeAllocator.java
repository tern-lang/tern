package org.ternlang.tree.define;

import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.instance.Instance;

public interface TypeAllocator {
   Instance allocate(Scope scope, Instance base, Object... list) throws Exception;
}