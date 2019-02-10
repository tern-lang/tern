package tern.tree.define;

import tern.core.function.Invocation;
import tern.core.function.InvocationBuilder;
import tern.core.scope.Scope;
import tern.core.scope.instance.Instance;

public class TypeDelegateAllocator implements TypeAllocator {
   
   private final InvocationBuilder builder;
   private final TypeAllocator allocator;
   
   public TypeDelegateAllocator(TypeAllocator allocator, InvocationBuilder builder) {
      this.allocator = allocator;
      this.builder = builder;
   }

   @Override
   public Instance allocate(Scope scope, Instance object, Object... list) throws Exception {
      Invocation invocation = builder.create(object);
      Instance base = (Instance)invocation.invoke(scope, object, list);
      
      return allocator.allocate(scope, base, list);
   }
}