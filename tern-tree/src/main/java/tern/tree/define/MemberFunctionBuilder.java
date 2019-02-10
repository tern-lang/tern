package tern.tree.define;

import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.function.FunctionBody;
import tern.core.type.TypeBody;

public interface MemberFunctionBuilder {
   FunctionBody create(TypeBody body, Scope scope, Type type);
}