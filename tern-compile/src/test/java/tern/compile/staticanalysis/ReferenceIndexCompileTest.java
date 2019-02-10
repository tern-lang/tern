package tern.compile.staticanalysis;

import junit.framework.TestCase;

import tern.compile.ClassPathCompilerBuilder;
import tern.compile.Compiler;

public class ReferenceIndexCompileTest extends TestCase {
   
   private static final String SOURCE =
   "import util.stream.Collectors;\n"+         
   "class Foo {\n"+
   "\n"+
   "   @Field\n"+
   "   var x;\n"+
   "\n"+
   "   @Constructor\n"+
   "   new(@Parameter x){\n"+
   "      this.x = x;\n"+
   "   }\n"+
   "}\n"+
   "var annotations = Foo.class\n"+
   "   .getFunctions()\n"+
   "   .stream()\n"+
   "   .filter(x -> x.name == 'new')\n"+
   "   .map(x -> x.getSignature().getParameters()[1].getAnnotations()) // param [0] is Type\n"+
   "   .collect(Collectors.toList());\n"+
   "\n"+
   "println(annotations);\n";
         
   public void testReferenceIndex() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE);
      compiler.compile(SOURCE).execute();
   }
}
