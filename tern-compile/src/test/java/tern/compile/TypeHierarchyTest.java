package tern.compile;

import tern.compile.Compiler;
import tern.compile.Executable;

import junit.framework.TestCase;

public class TypeHierarchyTest extends TestCase {

   private static final String SOURCE =
   "class X extends Y {\n"+
   "}\n"+
   "class Y extends X {\n"+
   "}\n"+
   "var types = X.getTypes();\n"+
   "println(types);\n"+
   "println(X.class);\n"+
   "println(Y.class);\n"+
   "println(X.class.getTypes());\n"+
   "println(Y.class.getTypes());\n";
   
   public void testHierarchy() throws Exception {
      boolean failure = false;
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE);
      Executable executable = compiler.compile(SOURCE);
      try {
         executable.execute();
      }catch(Exception e){
         e.printStackTrace();
         failure = true;
      }
      assertTrue("Failed to detect cycle", failure);
   }

}
