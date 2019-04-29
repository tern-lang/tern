package org.ternlang.compile.perf;

import junit.framework.TestCase;

import org.ternlang.compile.ClassPathCompilerBuilder;
import org.ternlang.compile.Compiler;
import org.ternlang.core.Bug;

public class LinkedListIteratorTest extends TestCase {
   
   private static final String SOURCE =
   "class Item{\n"+
   "   var x = 0;\n"+
   "   var y = 0;\n"+
   "   new(x){\n"+
   "      this.x=x;\n"+
   "   }\n"+
   "}\n"+
   "var list = new ArrayList();\n"+
   "\n"+
   "for(var i = 0; i < 10000; i++){\n"+
   "   list.add(new Item(i));\n"+
   "}\n"+
   "\n"+
   "for(var n in 0..10){\n"+
   "   var start = System.currentTimeMillis();\n"+
   "   try {\n"+
   "      for(var i = 0; i < 100; i++) {\n"+
   "         var it = list.iterator();\n"+
   "\n"+
   "         while(it.hasNext()){\n"+
   "            var v = it.next();\n"+
   "            assert v != null;\n"+
   "         }\n"+
   "      }\n"+
   "   } finally {\n"+
   "      var finish = System.currentTimeMillis();\n"+
   "      println('iterator=' + (finish - start));\n"+
   "   }\n"+
   "\n"+
   "   start = System.currentTimeMillis();\n"+
   "   try {\n"+
   "      for(var i = 0; i < 100; i++) {\n"+
   "         var it = list.iterator;\n"+
   "\n"+
   "         while(it.hasNext){\n"+ // we don't use a function we use a property
   "            var v = it.next;\n"+ // no function, only property
   "            assert v != null;\n"+
   "         }\n"+
   "      }\n"+
   "   } finally {\n"+
   "      var finish = System.currentTimeMillis();\n"+
   "      println('iterator(prop)=' + (finish - start));\n"+
   "   }\n"+
   "\n"+
   "\n"+
   "   start = System.currentTimeMillis();\n"+   
   "   try {\n"+
   "      for(var i = 0; i < 100; i++) {\n"+
   "         for(var v in list){\n"+
   "            assert v != null;\n"+
   "         }\n"+
   "      }\n"+
   "   } finally {\n"+
   "      var finish = System.currentTimeMillis();\n"+
   "      println('foreach=' + (finish - start));\n"+
   "   }\n"+
   "\n"+
   "   start = System.currentTimeMillis();\n"+   
   "   try {\n"+
   "      for(var i = 0; i < 100; i++) {\n"+
   "         for(var x in 0..10000-1){\n"+
   "            var v = list[x];\n"+
   "            assert v != null;\n"+
   "         }\n"+
   "      }\n"+
   "   } finally {\n"+
   "      var finish = System.currentTimeMillis();\n"+
   "      println('for=' + (finish - start));\n"+
   "   }\n"+
   "}";   

   @Bug("this is slower than it should be")
   public void testListIteration() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE);
      compiler.compile(SOURCE).execute();
   }
}
