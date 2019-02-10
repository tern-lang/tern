package tern.compile;

import junit.framework.TestCase;

public class InstanceReferenceStaticTest extends TestCase {

   private static final String SOURCE =
   "class Dumper {\n"+
   "   static const VALUES = [1, 2, 3, 4, 5];\n"+
   //"   new(){}\n"+
   "\n"+
   "   dump(index){\n"+
   "      System.err.println(VALUES[index]);\n"+
   "      return VALUES[index];\n"+
   "   }\n"+
   "}\n"+
   "var d = new Dumper();\n"+
   "\n"+
   "System.err.println(d.dump(2));\n"+
   "System.err.println(d.dump(3));\n"+
   "System.err.println(d.dump(4));\n";  
   
   public void testStaticReference() throws Exception{
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE);
      Executable executable = compiler.compile(SOURCE);
      long start = System.currentTimeMillis();
      
       executable.execute();
       System.err.println("time="+(System.currentTimeMillis()-start));
   }
   
   public static void main(String[] list) throws Exception {
      new InstanceReferenceStaticTest().testStaticReference();
   }
}
