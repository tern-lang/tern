package org.ternlang.compile;

import junit.framework.TestCase;

public class MapKeyTest extends TestCase {

   private static final String SOURCE =
   "var id = 123;\n"+
   "var date = new Date();\n"+
   "var map = {id: 'id', date: 'date'};\n"+
   "assert map.get(id) == 'id';\n";
   
   public void testMapKey() throws Exception {
      System.err.println(SOURCE);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      executable.execute();
   }
   
}
