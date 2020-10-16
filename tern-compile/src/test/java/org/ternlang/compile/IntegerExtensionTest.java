package org.ternlang.compile;

import static org.ternlang.core.Reserved.GRAMMAR_SCRIPT;

import org.ternlang.core.Reserved;
import org.ternlang.parse.SyntaxCompiler;
import org.ternlang.parse.SyntaxNode;

import junit.framework.TestCase;

public class IntegerExtensionTest extends TestCase {

   private static final String SOURCE_1 =
   "assert -1.toBinaryString() == '11111111111111111111111111111111';\n";

   private static final String SOURCE_2 =
   "println(-1.toBinaryString());\n"+
   "assert 1.toBinaryString() == '1';\n"+
   "assert Integer.MAX_VALUE.toBinaryString() == '1111111111111111111111111111111';\n"+
   "assert -1.toBinaryString() == '11111111111111111111111111111111';\n";

   public void testSignedNumber() throws Exception {
      SyntaxNode node = new SyntaxCompiler(Reserved.GRAMMAR_FILE).compile().parse("/path.tern", SOURCE_1, GRAMMAR_SCRIPT);
      System.out.println(SyntaxPrinter.print(node));
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_1);
      executable.execute();
   }

   public void testBinaryString() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_2);
      executable.execute();
   }
}
