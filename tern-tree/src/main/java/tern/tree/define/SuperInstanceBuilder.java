package tern.tree.define;

import tern.core.module.Module;
import tern.core.scope.Scope;
import tern.core.scope.instance.Instance;
import tern.core.scope.instance.SuperInstance;
import tern.core.type.Type;
import tern.core.variable.Value;

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
