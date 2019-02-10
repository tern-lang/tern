package tern.tree.define;

import tern.core.function.Invocation;
import tern.core.scope.Scope;
import tern.core.scope.instance.Instance;
import tern.core.type.Type;
import tern.core.type.TypeBody;
import tern.core.variable.Value;

public class ThisAllocator implements TypeAllocator {
   
   private final ObjectInstanceBuilder builder;
   private final Invocation invocation;
   private final TypeBody body;
   
   public ThisAllocator(TypeBody body, Invocation invocation, Type type) {
      this.builder = new ObjectInstanceBuilder(type);
      this.invocation = invocation;
      this.body = body;
   }
   
   @Override
   public Instance allocate(Scope scope, Instance base, Object... list) throws Exception {
      Type real = (Type)list[0];
      Instance instance = builder.create(scope, base, real); // we need to pass the base type up!
      Value value = instance.getThis();
      
      if(instance != base) { // false if this(...) is called
         value.setValue(instance); // set the 'this' variable
         body.execute(instance, real);
         invocation.invoke(instance, instance, list);
         
         return instance;
      }
      invocation.invoke(instance, instance, list);
      
      return instance;    
   }
}