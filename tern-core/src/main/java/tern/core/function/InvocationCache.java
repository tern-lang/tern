package tern.core.function;

import static tern.core.variable.Value.NULL;

import java.util.concurrent.atomic.AtomicInteger;

import tern.core.function.bind.FunctionMatcher;
import tern.core.function.dispatch.FunctionDispatcher;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.type.TypeExtractor;
import tern.core.variable.Value;

public class InvocationCache {
   
   private final TypeExtractor extractor;
   private final FunctionMatcher matcher;
   private final AtomicInteger counter;
   private final Connection[] cache;
   private final Object[] keys;
   
   public InvocationCache(FunctionMatcher matcher, TypeExtractor extractor) {
      this.counter = new AtomicInteger();
      this.cache = new Connection[3];
      this.keys = new Object[3];
      this.extractor = extractor;
      this.matcher = matcher;
   }

   public Invocation<?> fetch(Scope scope, Object[] array) throws Exception {
      Type type = scope.getType();
      int count = counter.get();
      
      for(int i = 0; i < count; i++) {
         if(i < cache.length) {
            Connection connection = cache[i];
            Object key = keys[i];
            
            if(connection != null && key == type) {
               if(cache[i].match(scope, NULL, array)) {
                  return cache[i];
               }
            }
         }
      }
      return fetch(scope, NULL, array, type);
   }

   public Invocation<?> fetch(Scope scope, Value value, Object[] array) throws Exception {
      Object object = value.getValue();
      Type type = extractor.getType(object);     
      int count = counter.get();
      
      for(int i = 0; i < count; i++) {
         if(i < cache.length) {
            Connection connection = cache[i];
            Object key = keys[i];
            
            if(connection != null && key == type) {
               if(cache[i].match(scope, value, array)) {
                  return cache[i];
               }
            }
         }
      }
      return fetch(scope, value, array, type);
   }

   private Invocation<?> fetch(Scope scope, Value value, Object[] array, Type type) throws Exception {
      FunctionDispatcher dispatcher = matcher.match(scope, value);
      Connection connection = dispatcher.connect(scope, value, array);
      int count = counter.get();
      
      if(connection != null && count < cache.length) {
         if(connection.match(scope, value, array)) { // can it be cached?
            int next = counter.getAndIncrement();
            
            if(next < cache.length) {
               cache[next] = connection;
               keys[next] = type;
            }
         }
      }
      return connection;
   }
}
