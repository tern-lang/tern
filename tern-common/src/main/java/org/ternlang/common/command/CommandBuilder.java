package org.ternlang.common.command;

import java.io.File;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.Callable;

public class CommandBuilder {
   
   private final String directory;
   private final boolean redirect;
   private final boolean wait;
   
   public CommandBuilder() {
      this(".");
   }
   
   public CommandBuilder(String directory) {
      this(directory, true);
   }
   
   public CommandBuilder(String directory, boolean redirect) {
      this(directory, redirect, false);
   }
   
   public CommandBuilder(String directory, boolean redirect, boolean wait) {
      this.directory = directory;
      this.redirect = redirect;
      this.wait = wait;
   }

   public Callable<Console> create(String source) throws Exception {
      return create(source, directory);
   }
   
   public Callable<Console> create(String source, String directory) throws Exception {
      return create(source, directory, Collections.EMPTY_MAP);
   }
   
   public Callable<Console> create(String source, Map<String, String> context) throws Exception {
      return create(source, directory, context);
   }
   
   public Callable<Console> create(String source, String directory, Map<String, String> context) throws Exception {
      File path = new File(directory);
      Environment environment = new MapEnvironment(context);
      Command command = new ProcessCommand(source, path, redirect, wait);
      
      return new CommandOperation(command, environment);
   }
}