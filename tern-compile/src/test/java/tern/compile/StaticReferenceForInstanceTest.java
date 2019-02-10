package tern.compile;

import junit.framework.TestCase;

public class StaticReferenceForInstanceTest extends TestCase {

   private static final String SOURCE_1=
   "class Circle {\n"+
   "   static const PI = 3.14;\n"+
   "   const pi = PI;\n"+
   "   var radius;\n"+
   "   new(radius){\n"+
   "      this.radius = radius;\n"+
   "      System.err.println(\"PI=${PI} pi=${pi}\");\n"+
   "   }\n"+
   "}\n"+
   "var c = new Circle(11);\n";
   
   private static final String SOURCE_2=
   "var c = new Circle(11);\n"+
   "class Circle {\n"+
   "   static const X = 11;\n"+
   "   static const Y = 334;\n"+
   "   const point = new Point(X, Y);\n"+
   "   var radius;\n"+
   "   new(radius){\n"+
   "      this.radius = radius;\n"+
   "      System.err.println(\"radius=\"+radius);\n"+
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
   "System.err.println(c.radius);\n";
   
   private static final String SOURCE_3=
   "class StaticVar {\n"+
   "   static const PI = 3.14;\n"+
   "   static const circle = new Circle(PI);\n"+
   "   static var count = 0;\n"+
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
   "   new(x, y){\n"+
   "      System.err.println(\"Point(\"+x+\", \"+y+\")\");\n"+
   "     this.x=x;\n"+
   "     this.y=y;\n"+
   "   }\n"+
   "}\n"+
   "System.err.println(\"static=\"+StaticVar.circle);\n";

   public void testStaticVariable() throws Exception{
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_1);
      Executable executable = compiler.compile(SOURCE_1);
      long start = System.currentTimeMillis();
      
      executable.execute();
      System.err.println("time="+(System.currentTimeMillis()-start));
   }
   
   public void testStaticVariableCreateInstance() throws Exception{
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_2);
      Executable executable = compiler.compile(SOURCE_2);
      long start = System.currentTimeMillis();
      
      executable.execute();
      System.err.println("time="+(System.currentTimeMillis()-start));
   }
   
   public void testStaticInstance() throws Exception{
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_3);
      Executable executable = compiler.compile(SOURCE_3);
      long start = System.currentTimeMillis();
      
      executable.execute();
      System.err.println("time="+(System.currentTimeMillis()-start));
   }
   
   public static void main(String[] list) throws Exception {
      new StaticReferenceForInstanceTest().testStaticVariable();
      new StaticReferenceForInstanceTest().testStaticVariableCreateInstance();
      new StaticReferenceForInstanceTest().testStaticInstance();
   }
}
