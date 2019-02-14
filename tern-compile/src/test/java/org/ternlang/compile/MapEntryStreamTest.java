package org.ternlang.compile;

import junit.framework.TestCase;

public class MapEntryStreamTest extends TestCase {
   
   private static final String SOURCE =
   "class Point {\n"+
   "   const x;\n"+
   "   const y;\n"+
   "   new(x, y){\n"+
   "      this.x = x;\n"+
   "      this.y = y;\n"+
   "   }\n"+
   "}\n"+
   "\n"+
   "var map = {:};\n"+
   "\n"+
   "map['a'] = new Point(1,2);\n"+
   "map['b'] = new Point(3,4);\n"+
   "\n"+
   "map.entrySet().stream().forEach(entry -> {\n"+
   "   println(entry.value.x);\n"+
   "});\n";
         
   public void testEntryStream() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE);
      executable.execute(); 
   }

}
