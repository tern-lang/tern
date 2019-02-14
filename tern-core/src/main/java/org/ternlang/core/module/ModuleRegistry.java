package org.ternlang.core.module;

import static org.ternlang.core.type.Phase.COMPILE;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;

import org.ternlang.common.Progress;
import org.ternlang.core.Context;
import org.ternlang.core.NameChecker;
import org.ternlang.core.error.InternalArgumentException;
import org.ternlang.core.type.Phase;
import org.ternlang.core.type.extend.ModuleExtender;

public class ModuleRegistry {

   private final Map<String, Module> modules;
   private final List<Module> references;
   private final ModuleAllocator allocator;
   private final PathConverter converter;
   private final ModuleExtender extender;
   private final NameChecker checker;
   
   public ModuleRegistry(Context context, Executor executor) {
      this(context, executor, 100000);
   }
   
   public ModuleRegistry(Context context, Executor executor, int limit) {
      this.allocator = new ModuleAllocator(context, executor, limit);
      this.modules = new ConcurrentHashMap<String, Module>();
      this.references = new CopyOnWriteArrayList<Module>();
      this.extender = new ModuleExtender(context);
      this.converter = new FilePathConverter();
      this.checker = new NameChecker(true);
   }
   
   public synchronized List<Module> getModules() {
      return references;
   }

   public synchronized Module getModule(String name) {
      if (name == null) {
         throw new InternalArgumentException("Module name was null");
      }
      return modules.get(name);
   }

   public synchronized Module addModule(String name) {
      if (name == null) {
         throw new InternalArgumentException("Module name was null");
      }
      Path path = converter.createPath(name);
      Module current = modules.get(name);
      
      if(current == null) {
         return addModule(name, path);
      }
      return current;
   }
   
   public synchronized Module addModule(String name, Path path) {
      if (name == null) {
         throw new InternalArgumentException("Module name was null");
      }
      Module current = modules.get(name);

      if (current == null) {
         Module module = allocator.allocate(name, path);
         Progress<Phase> progress = module.getProgress();
         
         if(!checker.isEntity(name)) {
            progress.done(COMPILE);   
         }          
         modules.put(name, module);
         extender.extend(module); 
         references.add(module);
         
         return module;
      }
      return current;
   }

}