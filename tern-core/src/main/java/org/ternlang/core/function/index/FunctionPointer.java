package org.ternlang.core.function.index;

import org.ternlang.core.function.Function;
import org.ternlang.core.function.Invocation;
import org.ternlang.core.scope.Scope;

public interface FunctionPointer {
   ReturnType getType(Scope scope);
   Function getFunction();
   Invocation getInvocation();
   Retention getRetention();
}
