package tern.tree.define;

import tern.core.error.InternalStateException;
import tern.core.function.Invocation;
import tern.core.function.InvocationBuilder;
import tern.core.scope.Scope;
import tern.core.scope.ScopeBinder;

public class InstanceInvocation implements Invocation<Scope> {

   private final InvocationBuilder builder;
   private final ScopeBinder binder;
   private final String name;
   private final boolean trait;

   public InstanceInvocation(InvocationBuilder builder, String name, boolean trait) {
      this.binder = new ScopeBinder();
      this.builder = builder;
      this.trait = trait;
      this.name = name;
   }
   
   @Override
   public Object invoke(Scope scope, Scope instance, Object... list) throws Exception {
      if(trait) {
         throw new InternalStateException("Function '" + name + "' is abstract");
      }
      Scope outer = binder.bind(scope, instance);
      Invocation invocation = builder.create(outer);
      
      return invocation.invoke(outer, instance, list);
   }
}