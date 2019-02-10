package tern.compile;

import junit.framework.TestCase;

public class DuplicateMethodTest extends TestCase {
   
   private static final String SOURCE_1 =
   "class DupMethods{\n"+
   "\n"+
   "   func(a, b){\n"+
   "   }\n"+
   "   func(x, y){\n"+
   "   }\n"+
   "}\n";

   private static final String SOURCE_2 =
   "class TypedDupMethods {\n"+
   "\n"+
   "   func(a: Integer){\n"+
   "   }\n"+
   "   func(b: Integer){\n"+
   "   }\n"+
   "}\n";

   private static final String SOURCE_3 =
   "class DupConstructor {\n"+
   "\n"+
   "   new(a: Integer){\n"+
   "   }\n"+
   "   new(b: Integer){\n"+
   "   }\n"+
   "}\n"+
   "new DupConstructor(1);\n";
   
   public void testMethodDuplicates() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      boolean failure = false;
      try {
         System.err.println(SOURCE_1);
         compiler.compile(SOURCE_1).execute();
      }catch(Exception e){
         e.printStackTrace();
         failure = true;
      }
      assertTrue("Should fail on dup methods", failure);
   }
   
   public void testTypedMethodDuplicates() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      boolean failure = false;
      try {
         System.err.println(SOURCE_2);
         compiler.compile(SOURCE_2).execute(); 
      }catch(Exception e){
         e.printStackTrace();
         failure = true;
      }
      assertTrue("Should fail on dup methods", failure);
   }
   
   public void testConstructorDuplicates() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      boolean failure = false;
      try {
         System.err.println(SOURCE_3);
         compiler.compile(SOURCE_3).execute(); 
      }catch(Exception e){
         e.printStackTrace();
         failure = true;
      }
      assertTrue("Should fail on dup constructors", failure);
   }
}
