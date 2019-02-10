package tern.compile;

import junit.framework.TestCase;

import tern.core.stack.StackOverflowException;

public class StackOverflowTest extends TestCase {
   
   private static final String SOURCE_1 =
   "class Obj {\n"+
   "   func(a: Integer){\n"+
   "      func(a++);\n"+
   "   }\n"+
   "}\n"+
   "new Obj().func(0);\n";
   
   private static final String SOURCE_2 =
   "class Obj {\n"+
   "   callMe(a: Integer){\n"+
   "      otherFunction(a++);\n"+
   "   }\n"+
   "   otherFunction(a: Integer){\n"+
   "      callMe(a++);\n"+
   "   }\n"+   
   "}\n"+
   " try {\n"+
   "   new Obj().callMe(0);\n"+
   "} catch(e) {\n"+
   "   throw new IllegalStateException('Error with function', e);\n"+
   "}";
   
   public void testStackOverflow() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_1);
      boolean failure = true;
      try {
         compiler.compile(SOURCE_1).execute();
      } catch(Exception e){
         e.printStackTrace();
         failure = true;
      }
      assertTrue("Stack overflow should have occurred", failure);
   }

   public void testStackOverflowRethrow() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_2);
      boolean failure = true;
      try {
         compiler.compile(SOURCE_2).execute();
      } catch(Exception e){
         e.printStackTrace();
         failure = true;
      }
      assertTrue("Stack overflow should have occurred", failure);
   }
}
