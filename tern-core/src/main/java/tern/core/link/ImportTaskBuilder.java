package tern.core.link;

import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executor;

import tern.core.Context;
import tern.core.Entity;
import tern.core.Execution;
import tern.core.NameFormatter;
import tern.core.Statement;
import tern.core.error.InternalStateException;
import tern.core.module.Module;
import tern.core.module.Path;
import tern.core.scope.Scope;
import tern.core.type.TypeLoader;

public class ImportTaskBuilder {

   private final ImportEntityResolver resolver;
   private final NameFormatter formatter;
   private final Executor executor;
   private final Module parent;
   private final Set failures;
   private final Set imports;
   private final Path from;
   
   public ImportTaskBuilder(Module parent, Executor executor, Path from) {
      this.resolver = new ImportEntityResolver(parent);
      this.failures = new CopyOnWriteArraySet<Path>();
      this.imports = new CopyOnWriteArraySet<Path>();
      this.formatter = new NameFormatter();
      this.executor = executor;
      this.parent = parent;
      this.from = from;
   }
   
   public Callable<Entity> createTask(String module, String name, Path path) throws Exception{
      if(!failures.contains(path)) {
         try {
            String qualifier = formatter.formatFullName(module, name);
            Context context = parent.getContext();
            TypeLoader loader = context.getLoader();
            Package entity = loader.importType(qualifier); // only explicit imports can be dynamic
            Package bundle = loader.importType(module, name); // load implicit and explicit
            
            return new DefineImport(bundle, entity, path, qualifier); // import exceptions will propagate
         } catch(Exception e) {
            failures.add(path);
         }
      }
      return null;
   }
   
   private class DefineImport implements Callable<Entity> {
      
      private final Package bundle;
      private final String name;
      private final Path path;
      
      public DefineImport(Package bundle, Package entity, Path path, String name){
         this.bundle = bundle;
         this.name = name;
         this.path = path;
      }

      @Override
      public Entity call() {
         try {
            if(!imports.contains(path)) {
               Scope scope = parent.getScope();
               PackageDefinition definition = bundle.create(scope); 
               Statement statement = definition.define(scope, from);
               
               if(imports.add(path)) {
                  Runnable task = new CompileImport(statement, path);
                  
                  if(executor != null) {
                     executor.execute(task); // compile must be asynchronous to avoid deadlock
                  } else {
                     task.run();
                  }
               }
            }
         } catch(Exception e) {
            throw new InternalStateException("Could not import '" + path+"'", e);
         }
         return resolver.resolveEntity(name);
      }
   }

   private class CompileImport implements Runnable {

      private final Statement statement;
      private final Path path;
      
      public CompileImport(Statement statement, Path path) {
         this.statement = statement;
         this.path = path;
      }
      
      @Override
      public void run() {
         try {
            Scope scope = parent.getScope();
            Execution execution = statement.compile(scope, null);
            
            execution.execute(scope);
         }catch(Exception e){
            throw new InternalStateException("Could not compile import '" + path +"'", e); // hidden exception
         }
      }
      
   }
}