package tern.tree;

import tern.core.Context;
import tern.core.Evaluation;
import tern.core.Execution;
import tern.core.NameFormatter;
import tern.core.NoStatement;
import tern.core.Statement;
import tern.core.constraint.Constraint;
import tern.core.error.InternalStateException;
import tern.core.link.ImportManager;
import tern.core.link.Package;
import tern.core.link.PackageDefinition;
import tern.core.module.Module;
import tern.core.module.Path;
import tern.core.scope.Scope;
import tern.core.type.TypeLoader;
import tern.core.variable.Value;

public class ImportBuilder {

   private final Qualifier qualifier;
   private final Evaluation alias;   
   
   public ImportBuilder(Qualifier qualifier, Evaluation alias) {
      this.qualifier = qualifier;
      this.alias = alias;
   }
   
   public Statement create(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      TypeLoader loader = context.getLoader();
      String location = qualifier.getLocation();
      String target = qualifier.getTarget();
      String name = qualifier.getName();
      
      if(target == null) {
         Package library = loader.importPackage(location);
         
         if(library != null) {
            return new CompileResult(library, path, location, null, name);
         }
      }
      Package library = loader.importType(location, target);

      if(library != null) {
         if(alias != null) {
            Scope scope = module.getScope();
            Value value = alias.evaluate(scope, null);
            String alias = value.getString();
            
            return new CompileResult(library, path, location, target, alias);
         } 
         return new CompileResult(library, path, location, target, target, name);
      }
      return new NoStatement();
   }
   
   private static class CompileResult extends Statement {
      
      private PackageDefinition definition;
      private NameFormatter formatter;
      private Statement statement;
      private Package library;
      private Path path;
      private String location;
      private String target;
      private String[] alias;

      public CompileResult(Package library, Path path, String location, String target, String... alias) {
         this.formatter = new NameFormatter();
         this.location = location;
         this.library = library;
         this.target = target;
         this.alias = alias;
         this.path = path;
      }
      
      @Override
      public void create(Scope scope) throws Exception {
         if(library == null) {
            throw new InternalStateException("Import '" + location + "' was not loaded");
         }
         if(definition == null) { // define once
            definition = process(scope);
         }
      }

      @Override
      public boolean define(Scope scope) throws Exception {
         if(definition == null) {
            throw new InternalStateException("Import '" + location + "' was not defined");
         }
         if(statement == null) { // compile once
            statement = definition.define(scope, path);
         }
         return true;
      }
      
      @Override
      public Execution compile(Scope scope, Constraint returns) throws Exception {
         if(statement == null) {
            throw new InternalStateException("Import '" + location + "' was not compiled");
         }
         return statement.compile(scope, returns); // execute many times
      }
      
      private PackageDefinition process(Scope scope) throws Exception {
         Module module = scope.getModule();
         ImportManager manager = module.getManager();
         String type = formatter.formatFullName(location, target);
             
         if(target == null) {
            manager.addImport(location); // import game.tetris.*;
         }  else {
            if(alias != null) {
               for(String name : alias) {
                  if(name != null) {
                     manager.addImport(type, name); // import game.tetris.Block as Shape;
                  }
               }
            } else {
               manager.addImport(type, target); // import game.tetris.Block;
            }
         }
         return library.create(scope);
      }
   }
}
