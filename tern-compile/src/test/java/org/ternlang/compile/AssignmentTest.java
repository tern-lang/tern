package org.ternlang.compile;

import org.ternlang.compile.Compiler;
import org.ternlang.compile.Executable;

import junit.framework.TestCase;

public class AssignmentTest extends TestCase{
   
   private static final String SOURCE_1=
   "class Point{\n"+
   "   var x:Double;\n"+
   "   var y:Double;\n"+
   "   new(x,y){\n"+
   "      this.x=x;\n"+
   "      this.y=y;\n"+
   "   }\n"+
   "   dump(){\n"+
   "      System.err.println(\"${x},${y}\");\n"+
   "   }\n"+
   "}\n"+
   "var p = new Point(true,false);\n"+
   "p.dump();\n";
   
   private static final String SOURCE_2=   
   "var buffer = new ByteArrayOutputStream();\n"+
   "var data = new Byte[3];\n"+
   "var text = \"this is some text to add to the buffer!!\".getBytes(\"UTF-8\");\n"+
   "var source = new ByteArrayInputStream(text);\n"+
   "var count = 0;\n"+
   "\n"+
   "while((count = source.read(data)) != -1){\n"+
   "   buffer.write(data, 0, count);\n"+
   "}\n"+
   "var result = buffer.toString();\n"+
   "\n"+
   "println(result);\n"+
   "println(text);\n";
         
   public void testAssignmentConstraints() throws Exception{
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_1);
      boolean failure = false;
      
      try {
         executable.execute();
      }catch(Throwable e){
         failure=true;
         e.printStackTrace();
      }
      assertTrue(failure);
   }
   
   public void testAssignmentInCondition() throws Exception{
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_2);
      executable.execute();
   }
}
