package org.ternlang.compile;

import junit.framework.TestCase;

public class StringEscapeTest extends TestCase {

   private static final String SOURCE=
   "var p=\"c:\\temp\\log.txt\";"+
   "var x=p.replace('\\\\', '/');";

   public void testEscape() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      System.err.println(SOURCE);
      executable.execute();
   }

   public static void main(String[] list) throws Exception {
      new StringEscapeTest().testEscape();
   }
}
