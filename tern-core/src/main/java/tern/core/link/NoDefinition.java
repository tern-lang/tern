package tern.core.link;

import tern.core.NoStatement;
import tern.core.Statement;
import tern.core.module.Path;
import tern.core.scope.Scope;

public class NoDefinition implements PackageDefinition {

   @Override
   public Statement define(Scope scope, Path from) throws Exception {
      return new NoStatement();
   }

}