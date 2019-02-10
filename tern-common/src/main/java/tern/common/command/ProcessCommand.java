package tern.common.command;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public class ProcessCommand implements Command {

   private final CommandParser command;
   private final String original;
   private final File directory;
   private final boolean redirect;
   private final boolean wait;

   public ProcessCommand(String command, File directory) {
      this(command, directory, false);
   }

   public ProcessCommand(String command, File directory, boolean redirect) {
      this(command, directory, redirect, true);
   }

   public ProcessCommand(String command, File directory, boolean redirect, boolean wait) {
      this.command = new CommandParser(command);
      this.directory = directory;
      this.original = command;
      this.redirect = redirect;
      this.wait = wait;
   }

   @Override
   public Console execute(Environment environment) throws Exception {
      File path = directory.getCanonicalFile();

      if (!path.exists()) {
         throw new CommandException("Script directory '" + path + "' does not exist for " + original);
      }
      if (!path.isDirectory()) {
         throw new CommandException("Script directory '" + path + "' is not a directory for " + original);
      }
      return execute(environment, path);
   }

   private Console execute(Environment environment, File path) throws Exception {
      try {
         List<String> tokens = command.command();
         ProcessBuilder builder = environment.createProcess(tokens);

         if (path != null) {
            builder.directory(path);
         }
         if (redirect) {
            builder.redirectErrorStream(true);
         }
         Process process = builder.start();
         InputStream input = process.getInputStream();

         if (wait) {
            process.waitFor();
         }
         return new Console(input);
      } catch (Exception e) {
         throw new CommandException("Error executing " + original + " in directory " + path, e);
      }
   }
}