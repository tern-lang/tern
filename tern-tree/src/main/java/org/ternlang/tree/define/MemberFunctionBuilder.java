package org.ternlang.tree.define;

import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.function.FunctionBody;
import org.ternlang.core.type.TypeBody;

public interface MemberFunctionBuilder {
   FunctionBody create(TypeBody body, Scope scope, Type type);
}