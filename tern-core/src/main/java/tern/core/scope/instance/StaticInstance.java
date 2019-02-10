package tern.core.scope.instance;

import tern.core.module.Module;
import tern.core.scope.Scope;
import tern.core.type.Type;

public class StaticInstance extends PrimitiveInstance {

   public StaticInstance(Module module, Scope scope, Type type) {
      super(module, scope, type, type);
   }

}