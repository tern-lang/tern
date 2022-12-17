package org.ternlang.compile.define;

import junit.framework.TestCase;
import org.ternlang.compile.ClassPathCompilerBuilder;
import org.ternlang.compile.Compiler;

public class InlineTest extends TestCase {
   private static final String SOURCE_1 =
   "enum Move(x, y){\n" +
   "   UP(0, -1),\n" +
   "   DOWN(0, 1),\n" +
   "   LEFT(-1, 0),\n" +
   "   RIGHT(1, 0);\n" +
   "}\n" +
   "assert Move.UP.x == 0;\n" +
   "assert Move.UP.y == -1;\n" +
   "assert Move.DOWN.x == 0;\n" +
   "assert Move.DOWN.y == 1;\n" +
   "assert Move.LEFT.x == -1;\n" +
   "assert Move.LEFT.y == 0;\n" +
   "assert Move.RIGHT.x == 1;\n" +
   "assert Move.RIGHT.y == 0;\n";

   private static final String SOURCE_2 =
   "class Point(x, y){\n"+
    "   override toString(){\n"+
    "      `${x}, ${y}`;\n"+
    "   }\n"+
    "}\n" +
   "assert Point(1, 2).x == 1;\n"+
   "assert Point(1, 2).toString() == '1, 2';\n";

   public void testEnumInlineConstructor() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println();
      System.err.println(SOURCE_1);
      compiler.compile(SOURCE_1).execute();
      System.err.println();
   }

   public void testClassInlineConstructor() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println();
      System.err.println(SOURCE_2);
      compiler.compile(SOURCE_2).execute();
      System.err.println();

   }
}
