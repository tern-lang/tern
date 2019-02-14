package org.ternlang.core.link;

import java.util.concurrent.FutureTask;

import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.module.Path;
import org.ternlang.core.scope.Scope;

public class FuturePackage implements Package {
   
   private final FutureTask<Package> result;
   private final Path path;
   
   public FuturePackage(FutureTask<Package> result, Path path) {
      this.result = result;
      this.path = path;
   }
   
   @Override
   public PackageDefinition create(Scope scope) throws Exception {
      Package library = result.get();
      
      if(library == null) {
         throw new InternalStateException("Could not define '" + path + "'");
      }
      return library.create(scope);
   }      
}