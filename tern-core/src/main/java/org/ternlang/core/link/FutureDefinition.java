package org.ternlang.core.link;

import java.util.concurrent.FutureTask;

import org.ternlang.core.Statement;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.module.Path;
import org.ternlang.core.scope.Scope;

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