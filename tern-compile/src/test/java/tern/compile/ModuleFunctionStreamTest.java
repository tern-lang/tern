package tern.compile;

import junit.framework.TestCase;

public class ModuleFunctionStreamTest extends TestCase {

   private static final String SOURCE =
   "module Module {\n"+
   "   @Func\n"+
   "   func1(){}\n"+
   "   @Func\n"+
   "   func2(){}\n"+
   "}\n"+
   "Module.getFunctions()\n"+
   "   .stream()\n"+
   "   .forEach(f -> {\n"+
   "      println(f);\n"+
   "      f.getAnnotations()\n"+
   "         .stream()\n"+
   "         .forEach(annotation -> {\n"+
   "            println(annotation);\n"+
   "            assert annotation.getName().equals('Func');\n"+
   "            assert annotation.name.equals('Func');\n"+
   "         });\n"+
   "   });\n";
   
   public void testModuleStream() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE);
      Executable executable = compiler.compile(SOURCE);
      executable.execute();
   }
         
}
