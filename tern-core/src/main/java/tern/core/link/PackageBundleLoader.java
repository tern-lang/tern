package tern.core.link;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.FutureTask;

import tern.core.ResourceManager;
import tern.core.error.InternalStateException;
import tern.core.module.Path;
import tern.core.module.PathConverter;

public class PackageBundleLoader {

   private final ConcurrentMap<Path, Package> registry;
   private final PathConverter converter;
   private final ResourceManager manager;
   private final PackageLinker linker;

   public PackageBundleLoader(PackageLinker linker, ResourceManager manager, PathConverter converter){
      this.registry = new ConcurrentHashMap<Path, Package>();
      this.converter = converter;
      this.manager = manager;
      this.linker = linker;
   }

   public PackageBundle load(String... resources) throws Exception {
      List<Package> packages = new ArrayList<Package>();
      PackageBundle bundle = new PackageBundle(packages);
      
      for(String resource : resources) {
         Path path = converter.createPath(resource);
         
         try {
            Package module = load(path);
            
            if(module != null) {
               packages.add(module);
            }
         } catch(Exception e) {
            throw new InternalStateException("Error linking '" + path + "'", e);
         }
      }
      return bundle;
   }
   
   private Package load(Path path) throws Exception {
      Package module = registry.get(path);
      
      if(module == null) {
         String location = path.getPath();
         String source = manager.getString(location); // load source code
         
         if(source != null) {
            Executable executable = new Executable(path, source);
            FutureTask<Package> task = new FutureTask<Package>(executable);
            FuturePackage result = new FuturePackage(task, path);    
            
            if(registry.putIfAbsent(path, result) == null) {
               task.run(); // call a validate method here
            }
            return registry.get(path); // return the future package
         }
      }
      return module;  
   }
   
   private class Executable implements Callable<Package> {
      
      private final String source;
      private final Path path;
      
      public Executable(Path path, String source) {
         this.source = source;
         this.path = path;
      }

      @Override
      public Package call() throws Exception {
         try {
            Package result = linker.link(path, source);
            
            if(result != null){
               registry.put(path, result); // replace future with package
            }
            return result;
         } catch(Exception cause) {
            return new ExceptionPackage("Could not load library '" + path + "'", cause);
         }
      }
   }
}