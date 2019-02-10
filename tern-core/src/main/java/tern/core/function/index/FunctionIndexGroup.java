package tern.core.function.index;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import tern.common.Cache;
import tern.common.CopyOnWriteCache;
import tern.core.function.Function;
import tern.core.function.Origin;
import tern.core.function.Signature;
import tern.core.type.Type;

public class FunctionIndexGroup {

   private final List<FunctionPointer> group;
   private final Cache<Object, FunctionPointer> cache;
   private final FunctionPointerCollector collector;
   private final ParameterTypeExtractor extractor;
   private final FunctionKeyBuilder builder;
   private final FunctionReducer searcher;
   private final AtomicBoolean types;
   private final String name;
   
   public FunctionIndexGroup(FunctionReducer searcher, FunctionKeyBuilder builder, String name) {
      this.cache = new CopyOnWriteCache<Object, FunctionPointer>();
      this.group = new ArrayList<FunctionPointer>();
      this.collector = new FunctionPointerCollector(group);
      this.extractor = new ParameterTypeExtractor();
      this.types = new AtomicBoolean();
      this.searcher = searcher;
      this.builder = builder;
      this.name = name;
   }

   public FunctionPointer resolve(Type... list) throws Exception {
      int count = group.size();
      
      if(types.get()) {
         Object key = builder.create(name, list);
         FunctionPointer pointer = cache.fetch(key);
         
         if(pointer == null) {
            FunctionPointer match = searcher.reduce(group, name, list);
            Function function = match.getFunction();
            Signature signature = function.getSignature();
            
            if(signature.isAbsolute()) {
               cache.cache(key, match);
            }
            return validate(match);
         }
         return validate(pointer);
      }
      if(count > 0) {
         return group.get(count -1);
      }
      return null;
   }
   
   public FunctionPointer resolve(Object... list) throws Exception {
      int count = group.size();

      if(types.get()) {
         Object key = builder.create(name, list);
         FunctionPointer pointer = cache.fetch(key);
         
         if(pointer == null) {
            FunctionPointer match = searcher.reduce(group, name, list);
            Function function = match.getFunction();
            Signature signature = function.getSignature();
            
            if(signature.isAbsolute()) {
               cache.cache(key, match);
            }
            return validate(match);
         }
         return validate(pointer);
      }
      if(count > 0) {
         return group.get(count -1);
      }
      return null;
   }

   public void index(FunctionPointer pointer) throws Exception {
      Type[] list = extractor.extract(pointer);
      Object key = builder.create(name, list);
      int count = 0;

      for(int i = 0; i < list.length; i++) {
         Type type = list[i];

         if (type != null) {
            count++;
         }
      }
      collector.collect(key, pointer);
      types.set(count > 0);
   }

   private FunctionPointer validate(FunctionPointer pointer) throws Exception {
      Function function = pointer.getFunction();
      Signature signature = function.getSignature();
      Origin origin = signature.getOrigin();
      
      if(!origin.isError()) {
         return pointer;
      }
      return null;
   }
}