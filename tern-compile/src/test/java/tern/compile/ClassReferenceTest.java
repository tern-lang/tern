package tern.compile;

import junit.framework.TestCase;

import tern.compile.Compiler;
import tern.core.scope.EmptyModel;

public class ClassReferenceTest extends TestCase {

   private static final String SOURCE =
   "class X{}\n"+
   "System.err.println(X.class.getModule());\n";
   
   public void testClassReference() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE);
      compiler.compile(SOURCE).execute(new EmptyModel());
   }
}
