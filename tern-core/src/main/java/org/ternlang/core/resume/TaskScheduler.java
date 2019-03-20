package org.ternlang.core.resume;

import org.ternlang.core.scope.Scope;

public interface TaskScheduler {
   Promise schedule(Scope scope, Task task);
}
