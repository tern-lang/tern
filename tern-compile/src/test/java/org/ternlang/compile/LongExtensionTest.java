package org.ternlang.compile;

import static org.ternlang.core.Reserved.GRAMMAR_SCRIPT;

import org.ternlang.core.Reserved;
import org.ternlang.parse.SyntaxCompiler;
import org.ternlang.parse.SyntaxNode;

import junit.framework.TestCase;

public class LongExtensionTest extends TestCase {

   private static final String SOURCE_1 =
   "println(Long.MAX_VALUE.toBinaryString());\n"+
   "assert -1L.toBinaryString() == '1111111111111111111111111111111111111111111111111111111111111111';\n"+
   "assert Long.MAX_VALUE.toBinaryString() == '111111111111111111111111111111111111111111111111111111111111111';\n";

   public void testSignedNumber() throws Exception {
      SyntaxNode node = new SyntaxCompiler(Reserved.GRAMMAR_FILE).compile().parse("/path.tern", SOURCE_1, GRAMMAR_SCRIPT);
      System.out.println(SyntaxPrinter.print(node));
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_1);
      executable.execute();
   }

}
