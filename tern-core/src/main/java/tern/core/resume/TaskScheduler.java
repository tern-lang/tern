package tern.core.resume;

import tern.core.scope.Scope;

public interface TaskScheduler {
   Promise schedule(Scope scope, Task<Answer> task);
}
