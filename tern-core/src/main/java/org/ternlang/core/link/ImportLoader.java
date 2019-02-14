package org.ternlang.core.link;

import org.ternlang.common.Cache;
import org.ternlang.common.CopyOnWriteCache;
import org.ternlang.core.Any;
import org.ternlang.core.ContextClassLoader;

import java.lang.Package;

public class ImportLoader {
   
   private final Cache<String, Package> packages;
   private final Cache<String, Class> types;
   private final ClassLoader loader;
   
   public ImportLoader() {
      this.packages = new CopyOnWriteCache<String, Package>();
      this.types = new CopyOnWriteCache<String, Class>();
      this.loader = new ContextClassLoader(Any.class);
   }
   
   public Package loadPackage(String name) {
      try {
         if(!packages.contains(name)) {
            Package match = Package.getPackage(name); // this does not really work!!
            
            packages.cache(name, match);
         }
      }catch(Exception e) {
         packages.cache(name, null);
         return null;
      }
      return packages.fetch(name);
   }
   
   public Class loadClass(String name) {
      try {
         if(!types.contains(name)) {
            Class match = loader.loadClass(name);

            types.cache(name, match);
         }
      } catch(Exception e) {
         types.cache(name, null);
         return null;
      }
      return types.fetch(name);
      
   }
}