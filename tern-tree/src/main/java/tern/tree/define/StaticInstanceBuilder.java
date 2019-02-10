package tern.tree.define;

import tern.core.module.Module;
import tern.core.scope.Scope;
import tern.core.scope.instance.Instance;
import tern.core.scope.instance.StaticInstance;
import tern.core.type.Type;

public class StaticInstanceBuilder {
   
   private final Type type;
   
   public StaticInstanceBuilder(Type type) {
      this.type = type;
   }

   public Instance create(Scope scope, Instance base, Type real) throws Exception {      
      if(base == null) {
         Module module = type.getModule();
         Scope inner = real.getScope();
         
         return new StaticInstance(module, inner, real); // create the first instance
      }
      return base;
   }
}