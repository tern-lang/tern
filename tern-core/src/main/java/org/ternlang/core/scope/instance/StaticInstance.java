package org.ternlang.core.scope.instance;

import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;

public class StaticInstance extends PrimitiveInstance {

   public StaticInstance(Module module, Scope scope, Type type) {
      super(module, scope, type, type);
   }

}