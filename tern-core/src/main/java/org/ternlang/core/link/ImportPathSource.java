package org.ternlang.core.link;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class ImportPathSource {
   
   private volatile DefaultImportReader reader;
   private volatile ImportPath path;
   
   public ImportPathSource(String file) {
      this.reader = new DefaultImportReader(file);
   }
   
   public ImportPath getPath() {
      if(path == null) {
         DefaultPath local = new DefaultPath();
         
         for(DefaultImport entry : reader) {
            Set<String> imports = entry.getImports();
            Set<String> modules = entry.getModules();
            String name = entry.getAlias();
            
            if(entry.isInclude()) {
               local.defaults.addAll(modules);
            }
            for(String type : imports) {
               local.types.put(type, modules);
            }
            local.aliases.put(name, modules);
         }
         path = local;
      }
      return path;
   }
   
   private static class DefaultPath implements ImportPath {

      private final Map<String, Set<String>> aliases;
      private final Map<String, Set<String>> types;
      private final Set<String> defaults;
      
      public DefaultPath() {
         this.aliases = new LinkedHashMap<String, Set<String>>();
         this.types = new LinkedHashMap<String, Set<String>>();
         this.defaults = new LinkedHashSet<String>();
      }
      
      @Override
      public Map<String, Set<String>> getAliases() {
         return aliases;
      }

      @Override
      public Map<String, Set<String>> getTypes() {
         return types;
      }

      @Override
      public Set<String> getDefaults() {
         return defaults;
      }
   }
}