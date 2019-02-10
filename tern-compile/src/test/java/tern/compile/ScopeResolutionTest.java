package tern.compile;
import junit.framework.TestCase;

public class ScopeResolutionTest extends TestCase {
   
   private static final String SOURCE_1 =
   "class Foo{\n"+
   "   static const COUNT=3;\n"+
   "   const x = 120;\n"+
   "\n"+
   "   func(){\n"+
   "      for(var x =0;x<10;x++){\n"+
   "         assert x<20;\n"+
   "      }\n"+
   "      assert x == 120;\n"+
   "      assert COUNT == 3;\n"+
   "   }\n"+
   "}\n"+
   "var x = new Foo();\n"+
   "x.func();\n";

   private static final String SOURCE_2 =
   "class Foo{\n"+
   "   static const COUNT=3;\n"+
   "   const x = 120;\n"+
   "\n"+
   "   func(){\n"+
   "      for(var x in 0..10){\n"+
   "         assert x<20;\n"+
   "      }\n"+
   "      assert x == 120;\n"+
   "      assert COUNT == 3;\n"+
   "   }\n"+
   "}\n"+
   "var x = new Foo();\n"+
   "x.func();\n";

   public void testScopeResolutionInForLoop() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_1);
      executable.execute();
   }
   
   public void testScopeResolutionInForInLoop() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_2);
      executable.execute();
   }
}
