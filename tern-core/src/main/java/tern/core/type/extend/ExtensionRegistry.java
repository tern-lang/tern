package tern.core.type.extend;

import static tern.core.function.Origin.DEFAULT;

import java.util.Collections;
import java.util.List;

import tern.common.Cache;
import tern.common.CopyOnWriteCache;
import tern.common.LazyBuilder;
import tern.common.LazyCache;
import tern.core.error.InternalException;
import tern.core.function.Function;
import tern.core.module.Module;
import tern.core.type.Type;
import tern.core.type.TypeLoader;

public class ExtensionRegistry {

   private final Cache<Class, List<Function>> cache;
   private final ExtensionBuilder builder;
   
   public ExtensionRegistry(TypeLoader loader){
      this.builder = new ExtensionBuilder(loader);
      this.cache = new LazyCache<Class, List<Function>>(builder);
   }
   
   public void register(Class type, Class extension) {
      builder.register(type, extension);
   }
   
   public List<Function> extract(Class type) {
      return cache.fetch(type); // cached
   }
   
   private static class ExtensionBuilder implements LazyBuilder<Class, List<Function>> {      

      private final Cache<Class, Class> extensions;
      private final FunctionExtractor extractor;
      private final TypeLoader loader;
      
      public ExtensionBuilder(TypeLoader loader){
         this.extractor = new FunctionExtractor(loader, DEFAULT);
         this.extensions = new CopyOnWriteCache<Class, Class>();
         this.loader = loader;
      }
      
      public void register(Class type, Class extension) {
         extensions.cache(type, extension);
      }
      
      @Override
      public List<Function> create(Class type) {
         Class extension = extensions.fetch(type);
         
         if(extension != null) {
            try {
               Type match = loader.loadType(type);
               Module module = match.getModule();
               
               return extractor.extract(module, type, extension);
            } catch(Exception e) {
               throw new InternalException("Could not extend " + type, e);
            }
         }
         return Collections.emptyList();
      }
   }
}