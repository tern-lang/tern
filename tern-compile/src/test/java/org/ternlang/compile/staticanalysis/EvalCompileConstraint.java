package org.ternlang.compile.staticanalysis;

import junit.framework.TestCase;

import org.ternlang.compile.ClassPathCompilerBuilder;
import org.ternlang.compile.Compiler;
import org.ternlang.compile.verify.VerifyException;

public class EvalCompileConstraint extends TestCase {

   private static final String SOURCE =
   "class Foo{\n"+
   "   const x,y;\n"+
   "   new(x,y){\n"+
   "      this.x=x;\n"+
   "      this.y=y;\n"+
   "   }\n"+
   "}\n"+
   "eval('new Foo(1,2)').x;\n"; // eval should have no constraints
 
   public void testEvalHasNoConstraint() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE);
      try {
         compiler.compile(SOURCE).execute();
      }catch(VerifyException e){
         e.getErrors().get(0).getCause().printStackTrace();
         throw e;
      }
   }   
}
