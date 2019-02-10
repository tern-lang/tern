package tern.core;

import tern.core.result.Result;
import tern.core.scope.Scope;

public abstract class Execution {
   public abstract Result execute(Scope scope) throws Exception;
}
