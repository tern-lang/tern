package org.ternlang.common.command;

import java.io.InputStream;
import java.io.OutputStream;

public class Console  {

   private final OutputStream output;
   private final InputStream input;

   public Console(InputStream input, OutputStream output) {
      this.output = output;
      this.input = input;
   }

   public Console pipe(Console console) {
      ConsolePipe pipe = new ConsolePipe(input, console.output);
      Thread thread = new Thread(pipe);

      thread.setDaemon(true);
      thread.start();

      return new Console(console.input, null);
   }

   public ConsoleWriter writer() {
      return new ConsoleWriter(output);
   }

   public ConsoleReader reader() {
      return new ConsoleReader(input);
   }

   private static class ConsolePipe implements Runnable {

      private final OutputStream output;
      private final InputStream input;

      public ConsolePipe(InputStream input, OutputStream output) {
         this.output = output;
         this.input = input;
      }

      @Override
      public void run() {
         pipe();
      }

      private boolean pipe() {
         try {
            int count = 0;

            try {
               while ((count = input.read()) != -1) {
                  output.write(count);
                  output.flush();
               }
            } finally {
               input.close();
            }
         } catch(Throwable cause) {
            return false;
         }
         return true;
      }
   }
}