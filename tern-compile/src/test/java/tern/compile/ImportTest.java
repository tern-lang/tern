package tern.compile;

import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.awt.geom.Ellipse2D;

import junit.framework.TestCase;

public class ImportTest extends TestCase {
   
   private static final String SOURCE_1=
   "import awt.geom.Ellipse2D;\n"+
   "println(new Ellipse2D.Double(1.0,2.0,3,3.8));\n";

   private static final String SOURCE_2=
   "import awt.geom.Ellipse2D;\n"+
   "import lang.reflect.*;\n"+
   "import static lang.Integer.*;\n"+         
   "import static util.stream.Collectors.toMap;\n"+
   "import awt.geom.Line2D;\n"+
   "import awt.geom.Line2D.Double;\n"+
   "import awt.Color as RGB;\n"+
   "class HashMap{\n"+
   "   static dump() {\n"+
   "      println('x');\n"+
   "   }\n"+
   "}\n"+
   "println(new Ellipse2D.Double(1.0,2.0,3,3.8));\n"+
   "println(Field.class);\n"+
   "println(new Line2D$Double());\n"+
   "println(new Line2D.Double());\n"+
   "println(new Double());\n"+
   "println(new Line2D$Double().class);\n"+
   "println(new Line2D.Double().class);\n"+
   "println(new Double().class);\n"+
   "HashMap.dump();\n"+
   "println(RGB.black);";   
   
   public void testInnerClassImport() throws Exception {
      Double d = new Double();
      System.err.println(d);
      System.err.println(new Ellipse2D.Double(1,2,3,4));
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_1);
      executable.execute();
   }

   public void testImport() throws Exception {
      Double d = new Double();
      System.err.println(d);
      System.err.println(new Line2D.Double());
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_2);
      executable.execute();
   }
}
