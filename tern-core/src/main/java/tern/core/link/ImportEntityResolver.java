package tern.core.link;

import tern.core.Context;
import tern.core.Entity;
import tern.core.NameFormatter;
import tern.core.module.Module;
import tern.core.module.ModuleRegistry;
import tern.core.type.TypeLoader;

public class ImportEntityResolver {
   
   private final NameFormatter formatter;
   private final Module parent;
   
   public ImportEntityResolver(Module parent){
      this.formatter = new NameFormatter();
      this.parent = parent;
   }
   
   public Entity resolveEntity(String type) {
      Context context = parent.getContext();
      TypeLoader loader = context.getLoader();
      ModuleRegistry registry = context.getRegistry();
      Module match = registry.getModule(type);
      
      if(match == null) {
         return loader.loadType(type);
      }
      return match;
   }
   
   public Entity resolveEntity(String module, String name) {
      String type = formatter.formatFullName(module, name);
      Context context = parent.getContext();
      TypeLoader loader = context.getLoader();
      ModuleRegistry registry = context.getRegistry();
      Module match = registry.getModule(type);
      
      if(match == null) {
         return loader.loadType(type);
      }
      return match;
   }
}