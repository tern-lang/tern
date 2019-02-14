package org.ternlang.compile;

import junit.framework.TestCase;

public class DefaultMethodTest extends TestCase {

   private static final String SOURCE=
   "var list = ['a1', 'a2', 'b1', 'c2', 'c1'];\n"+
   "\n"+
   "list.stream()\n"+
   "    .filter((s) -> s.startsWith('c'))\n"+
   "    .map((s) -> s.toUpperCase())\n"+
   "    .sorted()\n"+
   "    .forEach((s) -> println(s));\n";

   
   public void testDefaultMethods() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      executable.execute();
   }

   public static void main(String[] list) throws Exception {
      new DefaultMethodTest().testDefaultMethods();
   }
}
