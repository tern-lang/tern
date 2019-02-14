package org.ternlang.tree.define;

import org.ternlang.core.Statement;
import org.ternlang.core.module.Module;

public class ModuleStatement implements ModulePart {
   
   private final Statement statement;
   
   public ModuleStatement(Statement statement) {
      this.statement = statement;
   }

   @Override
   public Statement define(ModuleBody body, Module module) throws Exception {
      return statement;
   }

}
