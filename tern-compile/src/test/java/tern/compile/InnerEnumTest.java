package tern.compile;

import junit.framework.TestCase;

public class InnerEnumTest extends TestCase {

   public enum Config{
      A_888,
      B_888,
      C_888
   }

   private static final String SOURCE_1 =
   "class Palatte {\n"+
   "\n"+
   "   enum Color {\n"+
   "      RED,\n"+
   "      GREEN,\n"+
   "      BLUE,\n"+
   "      YELLOW,\n"+
   "      PURPLE;\n"+
   "      toString(){\n"+
   "         return name;\n"+
   "      }\n"+
   "   }\n"+
   "\n"+
   "   class Dot {\n"+
   "      var x;\n"+
   "      var y;\n"+
   "      var radius;\n"+
   "      var color:Color;\n"+
   "\n"+
   "      new(x,y,radius,color: Color){\n"+
   "         this.x=x;\n"+
   "         this.y=y;\n"+
   "         this.radius = radius;\n"+
   "         this.color = color;\n"+
   "      }\n"+
   "\n"+
   "      draw(){\n"+
   "         println(\"x=${x} y=${y} radius=${radius} color=${color}\");\n"+
   "      }\n"+
   "   }\n"+
   "}\n"+
   "println(Palatte.Color.RED);\n";

   private static final String SOURCE_2 =
   "import tern.compile.InnerEnumTest;\n"+
   "println(InnerEnumTest.Config.A_888);\n";

   public void testInnerEnum() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_1);
      Executable executable = compiler.compile(SOURCE_1);
      executable.execute();
   }

   public void testNativeInnerEnum() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_2);
      Executable executable = compiler.compile(SOURCE_2);
      executable.execute();
   }
}
