package tern.tree.define;

import tern.core.module.Module;
import tern.core.platform.Bridge;
import tern.core.scope.Scope;
import tern.core.scope.instance.Instance;
import tern.core.scope.instance.ObjectInstance;
import tern.core.type.Type;
import tern.core.variable.Value;

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