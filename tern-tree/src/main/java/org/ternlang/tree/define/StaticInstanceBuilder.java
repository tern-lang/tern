package org.ternlang.tree.define;

import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.instance.Instance;
import org.ternlang.core.scope.instance.StaticInstance;
import org.ternlang.core.type.Type;

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