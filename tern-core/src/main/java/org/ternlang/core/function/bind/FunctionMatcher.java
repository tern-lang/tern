package org.ternlang.core.function.bind;

import org.ternlang.common.Cache;
import org.ternlang.common.CopyOnWriteCache;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.ErrorHandler;
import org.ternlang.core.function.dispatch.EmptyDispatcher;
import org.ternlang.core.function.dispatch.FunctionDispatcher;
import org.ternlang.core.function.dispatch.FunctionDispatcherBuilder;
import org.ternlang.core.function.dispatch.LocalDispatcher;
import org.ternlang.core.function.dispatch.TypeLocalDispatcher;
import org.ternlang.core.function.resolve.FunctionResolver;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.Value;

public class FunctionMatcher {
   
   private final Cache<Class, FunctionDispatcher> cache;
   private final FunctionDispatcherBuilder builder;
   private final FunctionDispatcher instance;
   private final FunctionDispatcher local;
   private final FunctionDispatcher empty;
   
   public FunctionMatcher(FunctionResolver resolver, ErrorHandler handler, String name) {
      this.builder = new FunctionDispatcherBuilder(resolver, handler, name);
      this.cache = new CopyOnWriteCache<Class, FunctionDispatcher>();
      this.instance = new TypeLocalDispatcher(resolver, handler, name);
      this.local = new LocalDispatcher(resolver, handler, name);
      this.empty = new EmptyDispatcher();
   }
   
   public FunctionDispatcher match(Scope scope) throws Exception {
      Type type = scope.getType();
      
      if(type != null) {
         return instance;
      }
      return local;
   }
   
   public FunctionDispatcher match(Scope scope, Constraint left) throws Exception {
      Type type = left.getType(scope);
      
      if(type != null) {
         return builder.create(scope, left);
      }
      return empty;
   }
   
   public FunctionDispatcher match(Scope scope, Value left) throws Exception {
      Type type = scope.getType();
      Object object = left.getValue();
      
      if(object != null) {
         Class key = object.getClass();
         FunctionDispatcher dispatcher = cache.fetch(key); // key seems wrong?
         
         if(dispatcher == null) { 
            dispatcher = builder.create(scope, key);
            cache.cache(key, dispatcher);
         }
         return dispatcher;
      }
      if(type != null) {
         return instance;
      }
      return local;
   }

}