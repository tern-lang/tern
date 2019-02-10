package tern.compile.staticanalysis;

public class InvalidConstructorTest extends CompileTestCase {

   private static final String FAILURE_1 =
   "new StringBuilder(1,2,3);\n";

   private static final String FAILURE_2 =
   "new StringBuilder(1,2);\n";

   public void testConstructors() throws Exception {
      assertCompileError(FAILURE_1, "Constructor 'new(lang.Integer, lang.Integer, lang.Integer)' not found for 'lang.StringBuilder' in /default.tern at line 1");
      assertCompileError(FAILURE_2, "Constructor 'new(lang.Integer, lang.Integer)' not found for 'lang.StringBuilder' in /default.tern at line 1");
   }

}
