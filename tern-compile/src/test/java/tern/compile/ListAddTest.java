package tern.compile;

import junit.framework.TestCase;

public class ListAddTest extends TestCase {
   
   private static final String SOURCE =
   "class Point{\n"+
   "  var x,y;\n"+
   "\n"+
   "  new(x, y) {\n"+
   "     this.x = x;\n"+
   "     this.y = y;\n"+
   "  }\n"+
   "}\n"+
   "var start = System.currentTimeMillis();\n"+
   "var l = [];\n"+
   "for(var i in 0..100000){\n"+
   "   var x = new Point(i,i);\n"+
   "   //l.add(x);\n"+
   "   l[i]=x;\n"+
   "   //l.add(new Point(i,i));\n"+
   "}\n"+
   "var end = System.currentTimeMillis();\n"+
   "System.err.println(end-start);\n";
   
   public void testListAdd() throws Exception{
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE);
      Executable executable = compiler.compile(SOURCE);
      Timer.timeExecution("testListAdd", executable); 
      Timer.timeExecution("testListAdd", executable); 
      Timer.timeExecution("testListAdd", executable); 
   }

}
