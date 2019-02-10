package tern.compile.staticanalysis;

import tern.core.Bug;

public class GenericConstructorCompileTest extends CompileTestCase {

   private static final String SUCCESS_1 =
   "class Foo<T: List<?>>{}\n"+
   "let x = Foo<List<String>>();\n"+
   "println(x.class);\n";
   
   private static final String SUCCESS_2 =
   "class Foo<T: Collection<?>>{}\n"+
   "let x = new Foo<Set<?>>();\n"+
   "println(x.class);\n";
   
   private static final String FAILURE_1 =
   "class Foo<T: List<?>>{}\n"+
   "let x = Foo<Locale>();\n"+
   "println(x.class);\n";
   
   private static final String FAILURE_2 =
   "class Foo<T: List<?>>{}\n"+
   "let x = new Foo<Locale>();\n"+
   "println(x.class);\n";

   private static final String FAILURE_3 =
   "let x = new HashMap<?, ?, ?>();\n"+
   "println(x.class);\n";

   private static final String FAILURE_4 =
   "let x = new HashMap<?>();\n"+
   "println(x.class);\n";

   private static final String FAILURE_5 =
   "let x: HashMap<?> = {:};\n"+
   "println(x.class);\n";

   private static final String FAILURE_6 =
   "let x: HashMap<?, List<?, ?>> = null;\n"+
   "println(x.class);\n";

   @Bug("this should verify generics")
   private static final String FAILURE_7 =
   "let x = HashMap<?>();\n"+
   "println(x.class);\n";

   public void testConstructorGenerics() throws Exception {
      assertCompileSuccess(SUCCESS_1);
      assertCompileSuccess(SUCCESS_2);
      assertCompileError(FAILURE_1, "Generic parameter 'T' does not match 'util.List<lang.Object>' in /default.snap at line 2");
      assertCompileError(FAILURE_2, "Generic parameter 'T' does not match 'util.List<lang.Object>' in /default.snap at line 2");
      assertCompileError(FAILURE_3, "Generic parameter count for 'util.HashMap' is invalid in /default.snap at line 1");
      assertCompileError(FAILURE_4, "Generic parameter count for 'util.HashMap' is invalid in /default.snap at line 1");
      assertCompileError(FAILURE_5, "Generic parameter count for 'util.HashMap' is invalid in /default.snap at line 1");
      assertCompileError(FAILURE_6, "Generic parameter count for 'util.List' is invalid in /default.snap at line 1");
      //assertCompileError(FAILURE_7, "Generic parameter count for 'util.List' is invalid in /default.snap at line 1");
   }
}
