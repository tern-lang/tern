package org.ternlang.compile;

import junit.framework.TestCase;

public class InnerTraitTest extends TestCase {
   
   private static final String SOURCE=
   "class Table {\n"+
   "\n"+
   "   const list;\n"+
   "   new(){\n"+
   "      this.list =[];\n"+
   "   }\n"+
   "   create(key, cells){\n"+
   "      var r: Row = new StringRow(key,cells);\n"+
   "      list.add(r);\n"+
   "   }\n"+
   "   dump(){\n"+
   "      for(var row in list){\n"+
   "         row.dump();\n"+
   "      }\n"+
   "   }\n"+
   "\n"+
   "   trait Row{\n"+
   "      dump();\n"+
   "   }\n"+
   "\n"+
   "   class StringRow with Row {\n"+
   "      const key;\n"+
   "      const cells;\n"+
   "      new(x, y){\n"+
   "         this.key = x;\n"+
   "         this.cells = y;\n"+
   "      }\n"+
   "      dump(){\n"+
   "         for(var cell in cells){\n"+
   "            println(cell);\n"+
   "         }\n"+
   "      }\n"+
   "   }\n"+
   "}\n"+
   "\n"+
   "var table = new Table();\n"+
   "table.create(\"a\", [1,2,3,4,5]);\n"+
   "table.create(\"b\", ['a','b','c','d']);\n"+
   "table.create(\"c\", ['X','Y','Z']);\n"+
   "table.dump();\n";

   
   public void testInnerClass() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE);
      Executable executable = compiler.compile(SOURCE);
      executable.execute();
   }
}
