package tern.core.link;

import tern.core.scope.Scope;

public class NoPackage implements Package {

   @Override
   public PackageDefinition create(Scope scope) throws Exception {
      return new NoDefinition();
   }
}