package tern.compile.staticanalysis;

public class IncrementAndDecrementCompileTest extends CompileTestCase {

   private static final String FAILURE_1 = 
   "11++;\n";
   
   private static final String FAILURE_2 = 
   "'xx'++;\n";
   
   private static final String FAILURE_3 = 
   "++11;\n";
   
   private static final String FAILURE_4 = 
   "++'xx';\n";
   
   private static final String FAILURE_5 =
   "var x: String='a';\n"+         
   "x++;\n";   
   
   private static final String FAILURE_6 =
   "var x: String='a';\n"+         
   "++x;\n"; 
         
   private static final String FAILURE_7 =
   "var x: String='a';\n"+         
   "x--;\n";   
   
   private static final String FAILURE_8 =
   "var x: String='a';\n"+         
   "--x;\n"; 
   
   public void testModificationOfConstants() throws Exception {
      assertCompileError(FAILURE_1, "Illegal ++ of constant in /default.snap at line 1");
      assertCompileError(FAILURE_2, "Illegal ++ of constant in /default.snap at line 1");
      assertCompileError(FAILURE_3, "Illegal ++ of constant in /default.snap at line 1");
      assertCompileError(FAILURE_4, "Illegal ++ of constant in /default.snap at line 1");      
      assertCompileError(FAILURE_5, "Illegal ++ of type 'lang.String' in /default.snap at line 2");
      assertCompileError(FAILURE_6, "Illegal ++ of type 'lang.String' in /default.snap at line 2");
      assertCompileError(FAILURE_7, "Illegal -- of type 'lang.String' in /default.snap at line 2");
      assertCompileError(FAILURE_8, "Illegal -- of type 'lang.String' in /default.snap at line 2");        
   }

}
