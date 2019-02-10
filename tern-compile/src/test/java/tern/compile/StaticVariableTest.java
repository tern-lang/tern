package tern.compile;

import junit.framework.TestCase;

public class StaticVariableTest extends TestCase {

   private static final String SOURCE_1= 
   "class Math {\n"+
   "   static const PI = 3.14;\n"+
   "   const point = new Double(PI);\n"+      
   "}\n"+
   "new Math();";
   
   private static final String SOURCE_2=
   "class StaticVar {\n"+
   "   static const PI = 3.14;\n"+
   "   static const circle = new Circle(PI);\n"+
   "   static var count = 0;\n"+
   //"   new(){}\n"+
   "   static inc(){\n"+
   "      return count++;\n"+
   "   }\n"+
   "}\n"+
   "class Circle {\n"+
   "   static const X = 11;\n"+
   "   static const Y = 334;\n"+
   "   const point = new Point(X, Y);\n"+
   "   var pi;\n"+
   "   new(pi){\n"+
   "      this.pi = pi;\n"+
   "      System.err.println(\"pi=\"+pi);\n"+
   "   }\n"+
   "}\n"+
   "class Point {\n"+
   "   var x;\n"+
   "   var y;\n"+
   "\n"+
   "   new(x, y){\n"+
   "     this.x = x;\n"+
   "     this.y = y;\n"+
   "   }\n"+
   "}\n"+
   "System.err.println(\"static=\"+StaticVar.PI);\n"+
   "System.err.println(StaticVar.inc());\n"+   
   "System.err.println(StaticVar.inc());\n"+
   "System.err.println(StaticVar.inc());\n";

   public void testSimpleStaticVariable() throws Exception{
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_1);
      Executable executable = compiler.compile(SOURCE_1);
      long start = System.currentTimeMillis();
      
      executable.execute();
      System.err.println("time="+(System.currentTimeMillis()-start));
   }
   
   public void testStaticVariable() throws Exception{
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_2);
      Executable executable = compiler.compile(SOURCE_2);
      long start = System.currentTimeMillis();
      
      executable.execute();
      System.err.println("time="+(System.currentTimeMillis()-start));
   }
}
