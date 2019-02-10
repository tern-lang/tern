package tern.core.link;

import java.util.concurrent.FutureTask;

import tern.core.Statement;
import tern.core.error.InternalStateException;
import tern.core.module.Path;
import tern.core.scope.Scope;

public class FutureDefinition implements PackageDefinition {
   
   private final FutureTask<PackageDefinition> result;
   private final Path path;
   
   public FutureDefinition(FutureTask<PackageDefinition> result, Path path) {
      this.result = result;
      this.path = path;
   }

   @Override
   public Statement define(Scope scope, Path from) throws Exception {
      PackageDefinition definition = result.get();
      
      if(definition == null) {
         throw new InternalStateException("Could not compile '" + path + "'");
      }
      return definition.define(scope, from);
   }     
}