package org.ternlang.compile;

import junit.framework.TestCase;

public class ImportAliasInModuleTest extends TestCase {

   private static final String SOURCE =
   "import util.concurrent.CountDownLatch as L;\n"+
   "import util.concurrent.CountDownLatch;\n"+  
   "import util.concurrent.ConcurrentHashMap;\n"+
   "import util.concurrent.CopyOnWriteArrayList;\n"+   
   "module Mod {\n"+
   "   import util.concurrent.ConcurrentHashMap as M;\n"+
   "   import util.concurrent.CopyOnWriteArrayList as L;\n"+
   //"   import util.HashSet as X;\n"+   
   "   getM(){\n"+
   "      println(M.class);\n"+
   "      return new M();\n"+
   "   }\n"+
   "   getL(){\n"+
   "      println(L.class);\n"+
   "      return new L();\n"+ // this is a CopyOnWriteArrayList
   "   }\n"+
   "}\n"+
   "var l = Mod.getL();\n"+
   "var m = Mod.getM();\n"+
   "println(l.class);\n"+
   "println(m.class);\n"+
   "assert m.class == ConcurrentHashMap.class;\n"+
   "assert l.class == CopyOnWriteArrayList.class;\n";
   

   public void testAssertions() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      executable.execute();
   }   
   
}
