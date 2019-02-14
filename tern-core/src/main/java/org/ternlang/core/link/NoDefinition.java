package org.ternlang.core.link;

import org.ternlang.core.NoStatement;
import org.ternlang.core.Statement;
import org.ternlang.core.module.Path;
import org.ternlang.core.scope.Scope;

public class NoDefinition implements PackageDefinition {

   @Override
   public Statement define(Scope scope, Path from) throws Exception {
      return new NoStatement();
   }

}