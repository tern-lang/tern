package tern.tree.define;

import tern.core.scope.Scope;
import tern.core.scope.instance.Instance;

public interface TypeAllocator {
   Instance allocate(Scope scope, Instance base, Object... list) throws Exception;
}