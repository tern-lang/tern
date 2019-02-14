package org.ternlang.common.command;

import java.util.concurrent.Callable;

public class CommandOperation implements Callable<Console> {
   
   private final Environment environment;
   private final Command command;
   
   public CommandOperation(Command command, Environment environment) {
      this.environment = environment;
      this.command = command;
   }
   
   @Override
   public Console call() throws Exception {
      return command.execute(environment);
   }
}