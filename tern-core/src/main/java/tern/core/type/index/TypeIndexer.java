package tern.core.type.index;

import java.util.concurrent.atomic.AtomicInteger;

import tern.common.Cache;
import tern.common.HashCache;
import tern.core.NameFormatter;
import tern.core.error.InternalStateException;
import tern.core.link.ImportScanner;
import tern.core.module.Module;
import tern.core.module.ModuleRegistry;
import tern.core.platform.PlatformProvider;
import tern.core.type.Type;
import tern.core.type.extend.ClassExtender;

public class TypeIndexer {

   private final Cache<Object, Type> types;
   private final NameFormatter formatter;
   private final ImportScanner scanner;
   private final PrimitiveLoader loader;
   private final ModuleRegistry registry;
   private final ClassIndexer indexer;
   private final AtomicInteger counter;
   private final int limit;
   
   public TypeIndexer(ModuleRegistry registry, ImportScanner scanner, ClassExtender extender, PlatformProvider provider) {
      this(registry, scanner, extender, provider, 100000);
   }
   
   public TypeIndexer(ModuleRegistry registry, ImportScanner scanner, ClassExtender extender, PlatformProvider provider, int limit) {
      this.indexer = new ClassIndexer(this, registry, scanner, extender, provider);
      this.types = new HashCache<Object, Type>();
      this.loader = new PrimitiveLoader(this); 
      this.formatter = new NameFormatter();    
      this.counter = new AtomicInteger(1); // consider function types which own 0
      this.registry = registry;
      this.scanner = scanner;
      this.limit = limit;
   }
   
   public synchronized Type loadType(String type) {
      Type done = types.fetch(type);

      if (done == null) {
         Class match = scanner.importType(type);
         
         if (match == null) {
            return loader.loadType(type);
         }
         return loadType(match);
      }
      return done;
   }

   public synchronized Type loadType(String module, String name) {
      String alias = formatter.formatFullName(module, name);
      Type done = types.fetch(alias);

      if (done == null) {
         Class match = scanner.importType(alias);
         
         if (match == null) {
            return loader.loadType(alias);            
         }
         return loadType(match);
      }
      return done;
   }

   public synchronized Type loadArrayType(String module, String name, int size) {
      String alias = formatter.formatArrayName(module, name, size);
      Type done = types.fetch(alias);
      
      if (done == null) {
         String type = formatter.formatFullName(module, name);
         Class match = scanner.importType(type, size);
         
         if (match == null) {
            if(size > 0) {
               return createArrayType(module, name, size);
            }
            return loadType(module, name); 
         }
         return loadType(match);
      }
      return done;
   }   
   
   public synchronized Type defineType(String module, String name, int modifiers) {
      String alias = formatter.formatFullName(module, name);
      Type done = types.fetch(alias);

      if (done == null) {
         Class match = scanner.importType(alias);
         
         if (match == null) {
            Type type = createType(module, name, modifiers);
            
            types.cache(type, type);
            types.cache(alias, type);
            
            return type;
         }
         return loadType(match);
      }
      return done;
   }
   
   public synchronized Type loadType(Class source) {
      Type done = types.fetch(source);
      
      if (done == null) {
         String alias = scanner.importName(source);
         String absolute = source.getName();
         Type type = createType(source);

         types.cache(source, type);
         types.cache(alias, type);
         types.cache(absolute, type);
         
         return type;
      }
      return done;
   }

   private synchronized Type createType(String module, String name, int modifiers) {
      String alias = formatter.formatFullName(module, name);
      String prefix = formatter.formatOuterName(module, name); 
      Module parent = registry.addModule(module);
      Type type = types.fetch(alias);
      
      if(type == null) {
         Type outer = types.fetch(prefix);
         int order = counter.getAndIncrement();
         
         if(order > limit) {
            throw new InternalStateException("Type limit of " + limit + " exceeded");
         }
         return new ScopeType(parent, outer, name, modifiers, order);
      }
      return type;
   }
   
   private synchronized Type createArrayType(String module, String name, int size) {
      String alias = formatter.formatArrayName(module, name, size);
      Module parent = registry.addModule(module);
      Type type = types.fetch(alias);
      
      if(type == null) {
         Type entry = loadArrayType(module, name, size -1);
         
         if(entry == null) {
            throw new InternalStateException("Type entry for '" +alias+ "' not found");
         }
         String array = formatter.formatArrayName(null, name, size);
         int order = counter.getAndIncrement();
         
         if(order > limit) {
            throw new InternalStateException("Type limit of " + limit + " exceeded");
         }
         return new ScopeArrayType(parent, array, entry, size, order); // name is wrong here ScopeArrayType?
      }
      return type;
   }
   
   private synchronized Type createType(Class source) {
      String alias = scanner.importName(source);
      Type type = types.fetch(alias);
      
      if(type == null) {
         String name = formatter.formatShortName(source);
         int order = counter.getAndIncrement();
         
         if(order > limit) {
            throw new InternalStateException("Type limit of " + limit + " exceeded");
         }
         return new ClassType(indexer, source, name, order);
      }
      return type;
   }
}