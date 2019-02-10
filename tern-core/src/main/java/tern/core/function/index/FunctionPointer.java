package tern.core.function.index;

import tern.core.function.Function;
import tern.core.function.Invocation;
import tern.core.scope.Scope;

public interface FunctionPointer {
   ReturnType getType(Scope scope);
   Function getFunction();
   Invocation getInvocation();
   Retention getRetention();
}
