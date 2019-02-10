package tern.core.link;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

import tern.common.Cache;
import tern.common.LazyBuilder;
import tern.common.LazyCache;
import tern.core.Context;
import tern.core.Entity;
import tern.core.NameChecker;
import tern.core.NameFormatter;
import tern.core.error.InternalStateException;
import tern.core.module.FilePathConverter;
import tern.core.module.Module;
import tern.core.module.Path;
import tern.core.module.PathConverter;
import tern.core.type.Type;
import tern.core.type.TypeLoader;

public class ImportProcessor implements ImportManager {

   private final Map<String, Type> generics;
   private final Map<String, String> aliases;
   private final Set<String> imports;
   private final ImportMatcher matcher;
   private final EntityFinder finder;
   private final Module parent;
   private final String from;
   private final String local;
   
   public ImportProcessor(Module parent, Executor executor, Path path, String from, String local) {
      this.generics = new ConcurrentHashMap<String, Type>();
      this.aliases = new ConcurrentHashMap<String, String>();
      this.imports = new CopyOnWriteArraySet<String>();
      this.matcher = new ImportMatcher(parent, executor, path, from);
      this.finder = new EntityFinder(parent);
      this.parent = parent;
      this.local = local;
      this.from = from;
   }
   
   @Override
   public void addImport(String prefix) {
      imports.add(prefix);
   }
   
   @Override
   public void addImport(String type, String alias) {
      aliases.put(alias, type);
   }
   
   @Override
   public void addImport(Type type, String alias) {
      generics.put(alias, type);
   }
   
   @Override
   public void addImports(Module module) {
      ImportManager manager = module.getManager();

      if(manager != null) {
         Set<String> types = aliases.keySet();
         
         for(String type : types) {
            String alias = aliases.get(type);
            
            if(alias != null) {
               manager.addImport(type, alias);
            }
         }
         for(String value : imports) {
            manager.addImport(value);
         }
      }
   }
   
   public Type getType(String name) throws Exception {
      Future<Entity> future = getImport(name); // import tetris.game.*
      
      if(future != null) {
         Entity entity = future.get();
         
         if(Type.class.isInstance(entity)) {
            return (Type)entity;
         }         
      }
      return null;
   }
   
   public Module getModule(String name) throws Exception {
      Future<Entity> future = getImport(name); // import tetris.game.*
      
      if(future != null) {
         Entity entity = future.get();
         
         if(Module.class.isInstance(entity)) {
            return (Module)entity;
         }         
      }
      return null;
   }
   
   @Override
   public Future<Entity> getImport(String name) {
      return finder.find(name);
   }   
   
   private class EntityFinder implements LazyBuilder<String, Future<Entity>> {
   
      private final Cache<String, Future<Entity>> cache;
      private final EntityResolver resolver;
      private final NameFormatter formatter;
      private final NameChecker filter;
      
      public EntityFinder(Module parent) {
         this.cache = new LazyCache<String, Future<Entity>>(this);
         this.resolver = new EntityResolver(parent);
         this.formatter = new NameFormatter();
         this.filter = new NameChecker(true);
      }
      
      public Future<Entity> find(String name) {
         return cache.fetch(name);
      }
            
      @Override
      public Future<Entity> create(String name) {
         try {                 
            String inner = formatter.formatInnerName(name);
            String origin = formatter.formatLocalName(name);

            if(!filter.isEntity(inner)) {
               return null;
            }
            if(!filter.isEntity(origin)) {
               return null;
            }
            if(origin.equals(local)) {
               return new ImportFuture<Entity>(parent);
            }
            return resolver.resolve(name);
         } catch(Exception e){
            throw new InternalStateException("Could not find '" + name + "' in '" + from + "'", e);
         }
      }
   }
   
   private class EntityResolver {
      
      private final ImportEntityResolver resolver;
      private final PathConverter converter;
      private final NameFormatter formatter;
      
      public EntityResolver(Module parent) {
         this.resolver = new ImportEntityResolver(parent);
         this.converter = new FilePathConverter();
         this.formatter = new NameFormatter();
      }
      
      public Future<Entity> resolve(String name) throws Exception {
         String origin = formatter.formatLocalName(name);
         Future<Entity> entity = resolveGeneric(name);
         
         if(entity == null) {
            entity = resolveImport(name);
         }
         if(entity == null) {
            entity = resolveImport(origin);
         }
         if(entity == null) {
            entity = resolveDefault(name);
         }
         if(entity == null) {
            entity = resolveLocal(name);
         }
         return entity;
      }
      
      private Future<Entity> resolveGeneric(String name) throws Exception{
         Entity entity = generics.get(name);
         
         if(entity != null) {                  
            return new ImportFuture<Entity>(entity);
         } 
         return null;
      }
      
      private Future<Entity> resolveImport(String name) throws Exception{         
         String alias = aliases.get(name);
         int index = name.indexOf("."); 
         
         if(alias != null) {
            String suffix = formatter.formatShortName(name);
            String prefix = converter.createModule(alias);         
            Entity entity = resolver.resolveEntity(alias);
            
            if(entity == null) {
               entity = resolver.resolveEntity(prefix, suffix);
            }           
            if(entity != null) {            
               return new ImportFuture<Entity>(entity);
            }
         }  
         if(index == -1) {
            for(String module : imports) {
               Entity entity = resolver.resolveEntity(module, name); // this is "tetris.game.*"
               
               if(entity != null) {
                  return new ImportFuture<Entity>(entity);
               }
            }
         }
         return null;
      }
      
      private Future<Entity> resolveLocal(String name) throws Exception{
         Context context = parent.getContext();
         TypeLoader loader = context.getLoader();
         String origin = formatter.formatLocalName(name);
         int index = name.indexOf(".");     
         
         if(index == -1) {          
            Entity entity = loader.loadType(from, name);
               
            if(entity == null) {                      
               Future<? extends Entity> future = matcher.matchImport(imports, origin);
            
               if(name.endsWith(origin)) {
                  return (Future<Entity>)future;
               }
               entity = loader.loadType(from, name);
            }               
            if(entity != null) {                  
               return new ImportFuture<Entity>(entity);
            }            
         }  
         return null;
      }
      
      private Future<Entity> resolveDefault(String name) throws Exception{ 
         Context context = parent.getContext();
         TypeLoader loader = context.getLoader();       
         Entity entity = loader.loadType(null, name); // null is "java.*"
            
         if(entity != null) {                  
            return new ImportFuture<Entity>(entity);
         }            
         return null;
      }
   }
}