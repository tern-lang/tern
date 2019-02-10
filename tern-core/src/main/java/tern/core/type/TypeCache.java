package tern.core.type;

import tern.common.LazyBuilder;
import tern.common.LazyCache;
import tern.core.NameFormatter;
import tern.core.type.index.TypeIndexer;

public class TypeCache {
   
   private final NameFormatter formatter;
   private final StringResolver strings;
   private final ClassResolver classes;
   
   public TypeCache(TypeIndexer indexer){
      this.strings = new StringResolver(indexer);
      this.classes = new ClassResolver(indexer);
      this.formatter = new NameFormatter();
   }
   
   public Type fetch(String module, String name) {
      String type = formatter.formatFullName(module, name);
      return strings.resolve(type);
   } 
   
   public Type fetch(String type) {
      return strings.resolve(type);
   } 
   
   public Type fetch(Class type) {
      return classes.resolve(type);
   } 

   private static class StringResolver implements LazyBuilder<String, Type> {

      private final LazyCache<String, Type> cache;
      private final TypeIndexer indexer;
      
      public StringResolver(TypeIndexer indexer) {
         this.cache = new LazyCache<String, Type>(this);
         this.indexer = indexer;
      }
      
      public Type resolve(String name) {
         return cache.fetch(name);
      }
      
      @Override
      public Type create(String type) {
         return indexer.loadType(type);
      }
   }
   
   private static class ClassResolver implements LazyBuilder<Class, Type> {

      private final LazyCache<Class, Type> cache;
      private final TypeIndexer indexer;
      
      public ClassResolver(TypeIndexer indexer) {
         this.cache = new LazyCache<Class, Type>(this);
         this.indexer = indexer;
      }
      
      public Type resolve(Class type) {
         return cache.fetch(type);
      }
      
      @Override
      public Type create(Class type) {
         return indexer.loadType(type);
      }
   }
}
