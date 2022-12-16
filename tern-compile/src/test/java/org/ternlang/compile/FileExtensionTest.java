package org.ternlang.compile;

import junit.framework.TestCase;

public class FileExtensionTest extends TestCase {

   private static final String SOURCE_1 =
   "const f = File.createTempFile('temp', 'tmp');\n" +
   "f.writeText('hello world');\n" +
   "f.encrypt('secret123');\n" +
   "f.decrypt('secret123');\n";

   public void testEncryptDecrypt() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_1);
      Executable executable = compiler.compile(SOURCE_1);
      Timer.timeExecution("testEncryptDecrypt", executable);
   }
}
