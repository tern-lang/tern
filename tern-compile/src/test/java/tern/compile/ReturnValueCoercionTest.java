package tern.compile;

import junit.framework.TestCase;

public class ReturnValueCoercionTest extends TestCase {
   
   private static final String SOURCE = 
   "class Foo{}\n"+
   "class Bar{}\n"+
   "function fun(): Bar{\n"+
   "   return new Foo();\n"+
   "}\n"+
   "var f = fun();\n"+
   "assert f.class == Foo.class;\n";
   
   public void testCoercion() throws Exception{
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE);
      Executable executable = compiler.compile(SOURCE);
      boolean failure = false;
      try {
         executable.execute();
      }catch(Exception e){
         failure = true;
         e.printStackTrace();
      }
      assertTrue(failure);
   }
         

}
