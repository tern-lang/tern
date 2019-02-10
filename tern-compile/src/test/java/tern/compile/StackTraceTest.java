package tern.compile;

import junit.framework.TestCase;

public class StackTraceTest extends TestCase {
   
   private static final String SOURCE_1 =
   "const list: List<String> = ['a', 'b', 'c'];\n"+
   "list.stream()\n"+
   "   .map(x -> x.toUpperCase())\n"+
   "   .filter(Objects::nonNull)\n"+
   "   .map(x -> `${x}${x}`)\n"+
   "   .forEach(x -> {\n"+
   "      throw new Exception(x);\n"+
   "   });\n";

   public void testStackTrace() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_1);
      try {
         executable.execute();
      }catch(Exception e){
         e.printStackTrace();
      }
   }
}
