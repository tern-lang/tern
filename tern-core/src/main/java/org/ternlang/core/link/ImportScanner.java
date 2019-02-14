package org.ternlang.core.link;

import static org.ternlang.core.Reserved.IMPORT_FILE;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.ternlang.common.Cache;
import org.ternlang.common.CopyOnWriteCache;
import org.ternlang.core.NameFormatter;
import org.ternlang.core.ResourceManager;

import java.lang.Package;

public class ImportScanner {

   private final Cache<String, Package> packages;
   private final Cache<String, Class> types;
   private final Cache<Object, String> names;
   private final ImportPathResolver selector;
   private final NameFormatter formatter;
   private final ImportLoader loader;
   private final Set<String> failures;

   public ImportScanner(ResourceManager manager) {
      this(manager, IMPORT_FILE);
   }
   
   public ImportScanner(ResourceManager manager, String file) {
      this.packages = new CopyOnWriteCache<String, Package>();
      this.names = new CopyOnWriteCache<Object, String>();
      this.types = new CopyOnWriteCache<String, Class>();
      this.failures = new CopyOnWriteArraySet<String>();
      this.selector = new ImportPathResolver(file);
      this.formatter = new NameFormatter();
      this.loader = new ImportLoader();
   }
   
   public Package importPackage(String name) {
      if(!failures.contains(name)) {
         Package result = packages.fetch(name);
         
         if(result == null) {
            List<String> paths = selector.resolvePath(name);

            for(String path : paths) {
               result = loadPackage(path);
               
               if(result != null) {
                  packages.cache(name, result);
                  return result;
               }
            }   
            failures.add(name); // not found!!
         }
         return result;
      }
      return null;
   }

   public Class importType(String name) {
      if(!failures.contains(name)) {
         Class type = types.fetch(name);

         if(type == null) {
            List<String> paths = selector.resolvePath(name);
            
            for(String path : paths) {
               type = loadType(path);
               
               if(type != null) {
                  types.cache(name, type);
                  return type;
               }
            }   
            failures.add(name); // not found!!
         }
         return type;
      }
      return null;
   }
   
   public Class importType(String name, int size) {
      Class type = importType(name);
      
      if(type != null && size < 4) {
         Object array = null;
         
         if(size > 0) {
            if(size == 1) {
               array = Array.newInstance(type, 0);
            } else if(size == 2){
               array = Array.newInstance(type, 0, 0);
            } else if(size == 3){
               array = Array.newInstance(type, 0, 0, 0);
            } 
            return array.getClass();
         }
         return type;
      }
      return null;
   }
   
   public String importName(Class type) {
      String result = names.fetch(type);
      
      if(result == null) {
         String absolute = formatter.formatFullName(type);
         String name = selector.resolveName(absolute);
               
         types.cache(absolute, type);
         types.cache(name, type);
         names.cache(type, name);

         return name;
      }
      return result;
   }
   
   public String importName(Package module) {
      String result = names.fetch(module);
      
      if(result == null) {
         String absolute = module.getName();
         String name = selector.resolveName(absolute);

         packages.cache(absolute, module);
         packages.cache(name, module);
         names.cache(module, name);
   
         return name;
      }
      return result;
   }
   
   private Class loadType(String name) {
      try {
         Class result = loader.loadClass(name);

         if(result != null) {
            types.cache(name, result);
         }
         return result;
      }catch(Exception e){
         return null;
      }
   }
   
   private Package loadPackage(String name) {
      try {
         Package result = loader.loadPackage(name);
         
         if(result != null) {
            packages.cache(name, result);
         }
         return result;
      }catch(Exception e){
         return null;
      }
   }
}