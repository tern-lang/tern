package tern.compile;

import junit.framework.TestCase;
import tern.compile.verify.VerifyException;

public class ArrayCoercionTest extends TestCase {

   private static final String SOURCE_1 =
   "var z : String[][] = [['a','b'],[]];\n"+
   "var i : Integer[][] = [['1','2', 3.0d, 11L],[2]];\n"+
   "\n"+
   "fun(z);\n"+
   "fun(i);\n"+
   "\n"+
   "function fun(s: String[][]){\n"+
   "   for(var e in s){\n"+
   "      fun(e);\n"+
   "   }\n"+
   "}\n"+
   "\n"+
   "function fun(s: String[]){\n"+
   "   for(var e in s){\n"+
   "      println(\"e=${e}\");\n"+
   "   }\n"+
   "}\n"+
   "\n"+
   "function fun(s: Float[][]){\n"+
   "   for(var e in s){\n"+
   "      fun(e);\n"+
   "   }\n"+
   "}\n"+
   "\n"+
   "function fun(s: Float[]){\n"+
   "   for(var e in s){\n"+
   "      println(\"e=${e}\");\n"+
   "   }\n"+
   "}\n";
   
   private static final String SOURCE_2 =
   "class Score with Comparable {\n"+
   "   var score;\n"+
   "   new(score){\n"+
   "      this.score = score;\n"+
   "   }\n"+
   "   compareTo(other){\n"+
   "      return Double.compare(score, other.score);\n"+
   "   }\n"+
   "   toString(){\n"+
   "      return \"${score}\";\n"+
   "   }\n"+
   "}\n"+
   "var array1: Score[] = [new Score(1.1), new Score(1.0), new Score(2.0)];\n"+
   "var array2: Comparable[] = array1;\n"+   
   "var array3: Score[] = array2 as Score[];\n"+  
   "var array4: Comparable[] = array3;\n"+    
   "\n"+
   "println(array1);\n"+  
   "println(array1.class);\n"+     
   "println(array2);\n"+  
   "println(array2.class);\n"+     
   "println(array3);\n"+  
   "println(array3.class);\n"+       
   "println(array4);\n"+        
   "println(array4.class);\n"; 
   
   private static final String SOURCE_3 =
   "var array: Byte[] = 'hello world'.getBytes();\n"+
   "for(var item in array){\n"+
   "   println(item);\n"+
   "}\n";
    
   public void testArrayCoercion() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      try {
         System.err.println(SOURCE_1);
         Executable executable = compiler.compile(SOURCE_1);
         executable.execute();
      }catch(VerifyException e){
         e.getErrors().get(0).getCause().printStackTrace();
         throw e;
      }
   }  
   
   public void testScopeArrayCoercion() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_2);
      System.err.println(SOURCE_2);
      executable.execute();
   }   
   
   public void testPrimitiveArrayCoercion() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_3);
      System.err.println(SOURCE_3);
      executable.execute();
   }
}  
