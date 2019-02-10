package tern.tree.define;

import tern.core.Statement;
import tern.core.module.Module;

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
