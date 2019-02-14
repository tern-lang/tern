package org.ternlang.tree.define;

import java.util.concurrent.atomic.AtomicBoolean;

import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.instance.Instance;
import org.ternlang.core.type.Type;
import org.ternlang.core.function.Invocation;
import org.ternlang.core.type.TypeBody;

public class NewInvocation implements Invocation<Instance>{
   
   private final StaticInstanceBuilder builder;
   private final TypeAllocator allocator;
   private final AtomicBoolean compile;
   private final TypeBody body;
   private final Type type;
   
   public NewInvocation(TypeBody body, TypeAllocator allocator, Type type) {
      this(body, allocator, type, true);
   }
   
   public NewInvocation(TypeBody body, TypeAllocator allocator, Type type, boolean compile) {
      this.builder = new StaticInstanceBuilder(type);
      this.compile = new AtomicBoolean(compile);
      this.allocator = allocator;
      this.body = body;
      this.type = type;
   }

   @Override
   public Object invoke(Scope scope, Instance base, Object... list) throws Exception {
      Type real = (Type)list[0];
      Instance inner = builder.create(scope, base, real);
      
      if(compile.compareAndSet(true, false)) {
         body.allocate(scope, type); // static stuff if needed
      }
      return allocator.allocate(scope, inner, list);
   }
}