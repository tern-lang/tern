package org.ternlang.compile;

import junit.framework.TestCase;

public class PrintTest extends TestCase {

   private static final String SOURCE =
   "class Test{\n"+
   "   static const LIST = [1,2,3,4];\n"+
   "   static var count = 0;\n"+
   "   static inc(){\n"+
   "      return count++;\n"+
   "   }\n"+
   "   static at(index){\n"+
   "      return LIST[index];\n"+
   "   }\n"+
   "   var x;\n"+
   "   var y;\n"+
   "   new(x,y){\n"+
   "      this.x=x;\n"+
   "      this.y=y;\n"+
   "   }\n"+
   "   dump(){\n"+
   "      System.out.println(x);\n"+
   "   }\n"+
   "}\n"+
   "for(var i = 0; i < 10; i++) {\n"+
   "   System.out.println(Test.inc());\n"+
   "   System.out.println(Test.inc());\n"+
   "   System.out.println(Test.at(0));\n"+
   "   System.out.println(Test.at(1));\n"+
   "   System.out.println(Test.at(2));\n"+
   "\n"+
   "   var t = new Test(33,44);\n"+
   "\n"+
   "   System.out.println(t);\n"+
   "   //System.out.println(t.dump());\n"+
   "\n"+
   "   System.out.println(Test.inc());\n"+
   "   System.out.println(Test.inc());\n"+
   "   System.out.println(Test.inc());\n"+
   "}\n";
         
   public void testPrint() throws Exception{
      System.err.println(SOURCE);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      long start = System.currentTimeMillis();
      
       executable.execute();
       System.err.println("time="+(System.currentTimeMillis()-start));
   }
   
   public static void main(String[] list) throws Exception {
      new PrintTest().testPrint();
   }
}
