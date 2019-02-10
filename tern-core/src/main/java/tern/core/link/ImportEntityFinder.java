package tern.core.link;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import tern.core.Entity;
import tern.core.NameFormatter;
import tern.core.module.FilePathConverter;
import tern.core.module.Module;
import tern.core.module.Path;
import tern.core.module.PathConverter;

public class ImportEntityFinder {

   private final ConcurrentMap<Path, Future<Entity>> entities;
   private final ImportTaskBuilder builder;
   private final PathConverter converter;
   private final NameFormatter formatter;
   private final Executor executor;
   
   public ImportEntityFinder(Module parent, Executor executor, Path from) {
      this.entities = new ConcurrentHashMap<Path, Future<Entity>>();
      this.builder = new ImportTaskBuilder(parent, executor, from);
      this.converter = new FilePathConverter();
      this.formatter = new NameFormatter();
      this.executor = executor;
   }
   
   public Future<Entity> findEntity(String module, String name) throws Exception{
      String qualifier = formatter.formatFullName(module, name);
      Path path = converter.createPath(qualifier);
      Callable<Entity> task = builder.createTask(module, name, path);
      
      if(task != null) {
         FutureTask<Entity> future = new FutureTask<Entity>(task);
         
         if(entities.putIfAbsent(path, future) == null) {
            if(executor != null) {
               executor.execute(future); // reduce the stack depth
            } else {
               future.run();
            }
            return future;
         }
         return entities.get(path);
      }
      return null;
   }
}