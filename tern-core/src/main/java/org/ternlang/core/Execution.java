package org.ternlang.core;

import org.ternlang.core.result.Result;
import org.ternlang.core.scope.Scope;

public abstract class Execution {
   public abstract Result execute(Scope scope) throws Exception;
}
