package tern.core.variable.index;

import java.util.concurrent.atomic.AtomicReference;

import tern.common.Cache;
import tern.common.CopyOnWriteCache;
import tern.core.constraint.Constraint;
import tern.core.convert.proxy.ProxyWrapper;
import tern.core.scope.Scope;
import tern.core.type.Type;

public class VariableIndexer {
   
   private final AtomicReference<VariablePointer> reference;
   private final Cache<Integer, VariablePointer> cache;
   private final VariablePointerBuilder builder;
   private final VariableIndexResolver resolver;
   private final VariablePointer empty;
   
   public VariableIndexer(ProxyWrapper wrapper, String name) {
      this.cache = new CopyOnWriteCache<Integer, VariablePointer>();
      this.reference = new AtomicReference<VariablePointer>();
      this.builder = new VariablePointerBuilder(wrapper, name);
      this.resolver = new VariableIndexResolver();
      this.empty = new EmptyPointer();
   }
   
   public VariablePointer index(Scope scope) throws Exception {
      VariablePointer pointer = reference.get();

      if(pointer == null) { 
         pointer = builder.create(scope);
         reference.set(pointer);
      }
      return pointer;
   }

   public VariablePointer index(Scope scope, Object left) throws Exception {
      int index = resolver.resolve(scope, left);
      VariablePointer pointer = cache.fetch(index);

      if(pointer == null) { 
         pointer = builder.create(scope, left);
         cache.cache(index, pointer);
      }
      return pointer;
   }
   
   public VariablePointer index(Scope scope, Constraint left) throws Exception {
      Type type = left.getType(scope);
      
      if(type != null) {
         return builder.create(scope, left);
      }
      return empty;
   }
}