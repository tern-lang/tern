package tern.compile;

import tern.compile.verify.VerifyException;

import junit.framework.TestCase;

public class CoercionTest extends TestCase {

   private static final String SOURCE_1 =
   "class Foo{}\n"+
   "var f: Foo[] = null;\n"+
   "var arr = new Object[1][2];\n"+
   "arr[0][0]=`text`;\n"+
   "var x: String[] = ['a', 'b'];\n"+
   "var y: [] = arr;\n"+
   "\n"+
   "println(y.class);\n"+
   "assert y instanceof List;\n"+
   "assert y[0].class == Object[].class;\n"+
   "assert y[0][0].class == String.class;\n"+
   "assert y[0][0] == `text`;\n";

   private static final String SOURCE_2 =
   "class Blah{\n"+
   "   func(a...): []{\n"+
   "      println(a.class);\n"+       
   "      assert a.class.getEntry() != null;\n"+
   "      return a;\n"+
   "   }\n"+
   "}\n"+
   "assert new Blah().func(1,2,3,4) instanceof List;\n";

   public void testCoercion() throws Exception {;
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_1);
      try{
         Executable executable = compiler.compile(SOURCE_1);
         executable.execute();
      }catch(VerifyException e){
         e.getErrors().get(0).getCause().printStackTrace();
         throw e;
      }catch(Exception e){
         e.printStackTrace();
         throw e;
      }
   }
    
   public void testReturnCoercion() throws Exception {;
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_2);
      Executable executable = compiler.compile(SOURCE_2);
      executable.execute();
   } 
}
