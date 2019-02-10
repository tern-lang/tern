package tern.compile;

import tern.core.scope.EmptyModel;

import junit.framework.TestCase;

public class ModuleImportTest extends TestCase {
   
   private static final String SOURCE =
   "module Mod {\n"+
   "   import util.concurrent.ConcurrentHashMap as M;\n"+
   "}\n"+
   "\n"+
   "var failure = false;\n"+
   "try{\n"+
   "   println(M.class);\n"+
   "}catch(e){\n"+
   "  e.printStackTrace();\n"+
   "  failure = true;\n;"+
   "}\n"+
   "assert failure;\n";
       
   public void testModuleImport() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      boolean failure = false;
      try {
         System.err.println(SOURCE);
         compiler.compile(SOURCE).execute(new EmptyModel());
      } catch(Exception e) {
         failure = true;
         e.printStackTrace();
      }
      assertTrue("Compile failure", failure);
   }   
}
