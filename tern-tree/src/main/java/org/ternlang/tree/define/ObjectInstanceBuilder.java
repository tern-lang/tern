package org.ternlang.tree.define;

import org.ternlang.core.module.Module;
import org.ternlang.core.platform.Bridge;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.instance.Instance;
import org.ternlang.core.scope.instance.ObjectInstance;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.Value;

public class ObjectInstanceBuilder {
 
   private final Type type;
   
   public ObjectInstanceBuilder(Type type) {
      this.type = type;
   }

   public Instance create(Scope scope, Instance base, Type real) throws Exception {
      Class actual = base.getClass();
      
      if(actual != ObjectInstance.class) { // false if this(...) is called
         Module module = type.getModule();
         Bridge object = base.getBridge();
         Value self = base.getThis();
         
         return new ObjectInstance(module, base, object, self, real); // create the first instance
      }
      return base;
   }
}