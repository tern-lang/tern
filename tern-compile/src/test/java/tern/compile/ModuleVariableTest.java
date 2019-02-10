package tern.compile;

import junit.framework.TestCase;

import tern.core.scope.EmptyModel;

public class ModuleVariableTest extends TestCase {
   
   private static final String SOURCE=
   "class Typ{}\n"+
   "var t = new Typ();\n"+
   "println(Typ.class.module.name);\n"+
   "println(\"${Typ.class.module.name}\");";
   
   
   
   public void testModuleVariable() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE);
      compiler.compile(SOURCE).execute(new EmptyModel());
   }
}
