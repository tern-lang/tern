package org.ternlang.core.link;

import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

import org.ternlang.core.Context;
import org.ternlang.core.Entity;
import org.ternlang.core.NameFormatter;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.ModuleRegistry;
import org.ternlang.core.module.Path;

public class ImportMatcher {

   private final ImportEntityFinder finder;
   private final NameFormatter formatter;
   private final Module parent;
   private final String from;
   
   public ImportMatcher(Module parent, Executor executor, Path path, String from) {
      this.finder = new ImportEntityFinder(parent, executor, path);
      this.formatter = new NameFormatter();
      this.parent = parent;
      this.from = from;
   }

   public Future<? extends Entity> matchImport(Set<String> imports, String name) throws Exception { // [tetris.*, tetris.game.*], Tetris
      Context context = parent.getContext();
      ModuleRegistry registry = context.getRegistry();

      for(String module : imports) {
         Module match = registry.getModule(module);
         
         if(match != parent && match != null) {
            ImportManager manager = match.getManager();
            Future<? extends Entity> entity = manager.getImport(name); // get imports from the outer module if it exists

            if(entity != null) {
               return entity;
            }
         }
      }
      for(String module : imports) {
         Future<Entity> entity = finder.findEntity(module, name);
         
         if(entity != null) {
            return entity;
         }
      }
      String type = formatter.formatFullName(from, name);
      Module module = registry.getModule(type);
      
      if(module == null){ 
         return finder.findEntity(from, name);
      }
      return null;
   }
}