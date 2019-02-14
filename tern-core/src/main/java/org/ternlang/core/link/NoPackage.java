package org.ternlang.core.link;

import org.ternlang.core.scope.Scope;

public class NoPackage implements Package {

   @Override
   public PackageDefinition create(Scope scope) throws Exception {
      return new NoDefinition();
   }
}