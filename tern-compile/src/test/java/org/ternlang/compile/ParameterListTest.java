package org.ternlang.compile;

import junit.framework.TestCase;

public class ParameterListTest extends TestCase {

   public void testParameterList() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile("function fun(a){}");
      executable.execute();
   }
   
   public void testClassParameterListWithModifiers() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile("class Foo{fun(const a){}}");
      executable.execute();
   }
   
   public void testParameterListWithModifiers() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile("function fun(const a){}");
      executable.execute();
   }
}
