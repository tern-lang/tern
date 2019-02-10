package tern.core.link;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicReference;

import tern.core.Context;
import tern.core.Statement;
import tern.core.module.Module;
import tern.core.module.ModuleRegistry;
import tern.core.module.Path;
import tern.core.scope.Scope;

public class StatementPackage implements Package {
   
   private final AtomicReference<PackageDefinition> reference;
   private final PackageDefinition definition;
   private final Statement statement;
   private final String name;
   private final Path path;
   
   public StatementPackage(Statement statement, Path path, String name) {
      this.definition = new StatementDefinition(statement, path, name);
      this.reference = new AtomicReference<PackageDefinition>();
      this.statement = statement;
      this.name = name;
      this.path = path;
   }

   @Override
   public PackageDefinition create(Scope scope) throws Exception {
      PackageDefinition value = reference.get();
      
      if(value == null) {
         Executable executable = new Executable(scope, path, name);
         FutureTask<PackageDefinition> task = new FutureTask<PackageDefinition>(executable);
         FutureDefinition result = new FutureDefinition(task, path);
         
         if(reference.compareAndSet(null, result)) {
            task.run();
            return result; // must be future package for errors
         }
      }
      return reference.get(); // return future package
   }
   
   private class Executable implements Callable<PackageDefinition> {

      private final Scope scope;
      private final String name;
      private final Path path;
      
      public Executable(Scope scope, Path path, String name) {
         this.scope = scope;
         this.name = name;
         this.path = path;
      }
      
      @Override
      public PackageDefinition call() throws Exception {
         try {
            Module module = scope.getModule();
            Context context = module.getContext();
            ModuleRegistry registry = context.getRegistry();
            Module library = registry.addModule(name, path); //  we shPhaseinclude path
            Scope inner = library.getScope();
           
            statement.create(inner);
         } catch(Exception cause) {
            return new ExceptionDefinition("Error occurred defining '" + path + "'", cause);
         }
         return definition;
      }
   }
}