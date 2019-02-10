package tern.compile.staticanalysis;

public class ForEachLoopCompileTest extends CompileTestCase {

   private static final String SUCCESS_1 =
   "var total = 0;\n"+
   "for(i: Integer in 1..10){\n"+         
   "   total += i.intValue();\n"+         
   "}\n"+
   "assert total == 55;";
   
   private static final String SUCCESS_2 =
   "var total = 0;\n"+
   "for(i in 1..10){\n"+
   "   total += i.intValue();\n"+         
   "}\n"+
   "assert total == 55;";

   private static final String SUCCESS_3 =
   "var total = 0;\n"+
   "for(var i in 1..10){\n"+
   "   total += i.intValue();\n"+         
   "}\n"+
   "assert total == 55;";
   
   private static final String SUCCESS_4 =
   "var total = 0;\n"+
   "for(var i: Integer in 1..10){\n"+
   "   total += i.intValue();\n"+         
   "}\n"+
   "assert total == 55;";
   
   private static final String SUCCESS_5 =
   "var total = '';\n"+
   "for(var i in ['a', 'b', 'c', 'd']){\n"+
   "   total += i.toUpperCase();\n"+         
   "}\n"+
   "println(total);\n"+
   "assert total == 'ABCD';";
   
   private static final String SUCCESS_6 =
   "var total = '';\n"+
   "for(var i: String in ['a', 'b', 'c', 'd']){\n"+
   "   total += i.toUpperCase();\n"+         
   "}\n"+
   "println(total);\n"+
   "assert total == 'ABCD';";    
   
   private static final String SUCCESS_7 =
   "var total = '';\n"+
   "for(i in ['a', 'b', 'c', 'd']){\n"+
   "   total += i.toUpperCase();\n"+         
   "}\n"+
   "println(total);\n"+
   "assert total == 'ABCD';"; 
   
   private static final String SUCCESS_8 =
   "var total = '';\n"+
   "for(i: String in ['a', 'b', 'c', 'd']){\n"+
   "   total += i.toUpperCase();\n"+         
   "}\n"+
   "println(total);\n"+
   "assert total == 'ABCD';";
   
   private static final String SUCCESS_9 =
   "var total = '';\n"+
   "for(i: Map.Entry in {'a': 1, 'b':2, 'c':3}){\n"+
   "   total += i.key.toUpperCase();\n"+      
   "}\n"+
   "println(total);\n"+
   "assert total == 'ABC';";
   
   private static final String SUCCESS_10 =
   "var total = '';\n"+
   "for(var i: Map.Entry in {'a': 1, 'b':2, 'c':3}){\n"+
   "   total += i.key.toUpperCase();\n"+         
   "}\n"+
   "println(total);\n"+
   "assert total == 'ABC';";   
   
   private static final String SUCCESS_11 =
   "var total = '';\n"+
   "for(var i in {'a': 1, 'b':2, 'c':3}){\n"+
   "   total += i.key.toUpperCase();\n"+
   "   assert i instanceof Map.Entry;\n"+
   "}\n"+
   "println(total);\n"+
   "assert total == 'ABC';"; 
   
   private static final String FAILURE_1 =
   "var total = '';\n"+
   "for(i: Integer in 1..10){\n"+
   "   total += i.toUpperCase();\n"+          
   "}\n";

   private static final String FAILURE_2 =
   "var total = '';\n"+
   "for(i: String in ['a','b','c','d']){\n"+
   "   total += i.intValue();\n"+          
   "}\n";
   
   public void testSimpleForEachLoop() throws Exception {
      assertCompileSuccess(SUCCESS_1);
      assertCompileSuccess(SUCCESS_2);
      assertCompileSuccess(SUCCESS_3);
      assertCompileSuccess(SUCCESS_4);
      assertCompileSuccess(SUCCESS_5);
      assertCompileSuccess(SUCCESS_6);
      assertCompileSuccess(SUCCESS_7);
      assertCompileSuccess(SUCCESS_8);      
      assertCompileSuccess(SUCCESS_9);
      assertCompileSuccess(SUCCESS_10);
      assertCompileSuccess(SUCCESS_11);      
      assertCompileError(FAILURE_1, "Function 'toUpperCase()' not found for 'lang.Integer' in /default.tern at line 3");
      assertCompileError(FAILURE_2, "Function 'intValue()' not found for 'lang.String' in /default.tern at line 3");
   }       
}
