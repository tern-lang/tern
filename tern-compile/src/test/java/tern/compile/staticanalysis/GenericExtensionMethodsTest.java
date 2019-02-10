package tern.compile.staticanalysis;

public class GenericExtensionMethodsTest extends CompileTestCase {
   
   private static final String SUCCESS_1 = 
   "new File('c:/temp').findFiles('.*.bak').forEach(file -> println(file));";
      
   private static final String SUCCESS_2 = 
   "new File('c:/temp').findFiles('.*.bak').stream().findFirst().get().lastModified();";
   
   private static final String SUCCESS_3 = 
   "new File('c:/temp').findPaths('.*.bak').stream().findFirst().get().toUpperCase();";
   
   private static final String FAILURE_1 = 
   "new File('c:/temp').findFiles('.*.bak').stream().findFirst().get().toUpperCase();";
   
   public void testExtensionMethods() throws Exception {
      assertCompileSuccess(SUCCESS_1);
      assertCompileSuccess(SUCCESS_2);
      assertCompileSuccess(SUCCESS_3);
      assertCompileError(FAILURE_1, "Function 'toUpperCase()' not found for 'io.File' in /default.tern at line 1");
   }
}
