package org.ternlang.compile;

import junit.framework.TestCase;

public class ReferenceStaticFieldTest extends TestCase {
   
   private static final String SOURCE = 
   "class Foo{\n"+
   "   static const BLAH = 11;\n"+
   "\n"+
   "   test(){\n"+
   "      var x = Integer.MAX_VALUE;\n"+
   "      for(var n in 0..10) {\n"+
   "         var s = System.currentTimeMillis();\n"+
   "         try {\n"+
   "            for(var i in 0..10000){\n"+
   "               assert Integer.MAX_VALUE > 0;\n"+
   "            }\n"+
   "         }finally {\n"+
   "            println('Integer.MAX_VALUE='+(System.currentTimeMillis()-s));\n"+
   "         }\n"+
   "         s = System.currentTimeMillis();\n"+
   "         try {\n"+
   "            for(var i in 0..10000){\n"+
   "               assert 10 > 0;\n"+
   "            }\n"+
   "         }finally {\n"+
   "            println('const='+(System.currentTimeMillis()-s));\n"+
   "         }\n"+
   "         s = System.currentTimeMillis();\n"+
   "         try {\n"+
   "            for(var i in 0..10000){\n"+
   "               assert x > 0;\n"+
   "            }\n"+
   "         }finally {\n"+
   "            println('var='+(System.currentTimeMillis()-s));\n"+
   "         }\n"+
   "         s = System.currentTimeMillis();\n"+
   "         try {\n"+
   "            for(var i in 0..10000){\n"+
   "               assert BLAH > 0;\n"+
   "            }\n"+
   "         }finally {\n"+
   "            println('static='+(System.currentTimeMillis()-s));\n"+
   "         }\n"+
   "      }\n"+
   "   }\n"+
   "}\n"+
   "new Foo().test();";

   public void testListIteration() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE);
      compiler.compile(SOURCE).execute();
   }
}
