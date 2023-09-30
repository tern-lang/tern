package org.ternlang.common.command;

import junit.framework.TestCase;

import java.util.Iterator;

public class CommandBuilderTest extends TestCase {

   public void testBasic() throws Exception{
      CommandBuilder executor = new CommandBuilder();
      Iterator<String> iterator = executor.create("echo 'hi'", ".")
           .call()
           .reader()
           .readLines()
           .iterator();

      while(iterator.hasNext()) {
         String entry = iterator.next();
         System.err.println(entry);
      }
   }
   
   public void testStream() throws Exception{
      CommandBuilder executor = new CommandBuilder();
      Iterator<String> iterator = executor.create("ls -al", ".")
           .call()
           .reader()
           .readLines()
           .iterator();
      
      while(iterator.hasNext()) {
         String entry = iterator.next();
         System.err.println(entry);
      }
   }

   public void testPipe() throws Exception{
      CommandBuilder executor = new CommandBuilder();
      Console console = executor.create("grep f").call();
      ConsoleWriter writer = console.writer();
      ConsoleReader reader = console.reader();

      writer.output.write('f');
      writer.output.write('\r');
      writer.output.write('\n');
      writer.output.flush();
      System.err.println((char)reader.read());

//      Iterator<String> iterator = executor.create("ls -al", ".")
//           .call()
//           .pipe(executor.create("grep f").call())
//              .reader()
//              .readLines()
//              .iterator();
//
//      while(iterator.hasNext()) {
//         String entry = iterator.next();
//         System.err.println(entry);
//      }
//      Thread.sleep(10000000);
   }
}
