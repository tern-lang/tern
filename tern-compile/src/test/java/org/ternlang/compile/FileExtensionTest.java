package org.ternlang.compile;

import junit.framework.TestCase;

public class FileExtensionTest extends TestCase {

   private static final String SOURCE_1 =
   "const f = File.createTempFile('temp', 'tmp');\n" +
   "f.writeText('hello world');\n" +
   "f.encrypt('secret123');\n" +
   "f.decrypt('secret123');\n";

   private static final String SOURCE_2 =
   "const f = File.createTempFile('temp2', 'tmp');\n" +
   "f.writeText('hello world');\n" +
   "f.zip();\n" +
   "assert File(f.getCanonicalPath() + '.zip').exists();\n"+
   "File(f.getCanonicalPath() + '.zip').unzip();\n";

   public void testEncryptDecrypt() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_1);
      Executable executable = compiler.compile(SOURCE_1);
      Timer.timeExecution("testEncryptDecrypt", executable);
   }

   public void testZipUnzip() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_2);
      Executable executable = compiler.compile(SOURCE_2);
      Timer.timeExecution("testZipUnzip", executable);
   }
}
