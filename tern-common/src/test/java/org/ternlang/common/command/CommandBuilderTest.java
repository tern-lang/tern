package org.ternlang.common.command;

import java.util.Iterator;

import junit.framework.TestCase;

public class CommandBuilderTest extends TestCase {
   
   public void testExecutor() throws Exception{
      CommandBuilder executor = new CommandBuilder();
      Iterator<String> iterator = executor.create("ls -al", ".").call().iterator();
      
      while(iterator.hasNext()) {
         String entry = iterator.next();
         System.err.println(entry);
      }
   }

}
