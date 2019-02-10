package tern.compile;

import junit.framework.TestCase;

public class TypeToClassCoercionTest extends TestCase {
   
   private static final String SOURCE =
   "import tern.compile.TypeToClassCoercionTest;\n"+
   "var str: String = 'hello world';\n"+
   "var int: Integer = 11;\n"+
   "\n"+
   "assert TypeToClassCoercionTest.dump(String.class);\n"+
   "assert TypeToClassCoercionTest.dump(Integer.class);\n"+
   "assert TypeToClassCoercionTest.dump(str.class);\n"+
   "assert TypeToClassCoercionTest.dump(int.class);\n";

   public void testCoercion() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE);
      compiler.compile(SOURCE).execute();
   }
   
   public static boolean dump(Class type) throws Exception {
      assertNotNull(type);
      System.err.println(type);
      return true;
   }
}
