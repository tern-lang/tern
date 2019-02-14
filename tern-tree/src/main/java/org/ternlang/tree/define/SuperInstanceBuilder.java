package org.ternlang.tree.define;

import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.instance.Instance;
import org.ternlang.core.scope.instance.SuperInstance;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.Value;

public class SuperInstanceBuilder {
   
   private final Type type;
   
   public SuperInstanceBuilder(Type type) {
      this.type = type;
   }

   public Scope create(Scope scope, Value left) throws Exception {
      Type real = left.getValue();
      Instance instance = (Instance)scope;
      Instance outer = instance.getScope();
      Module module = type.getModule();

      return new SuperInstance(module, outer, real, type);
   }
}
